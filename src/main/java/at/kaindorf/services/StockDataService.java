package at.kaindorf.services;

import at.kaindorf.models.StockDto;
import at.kaindorf.models.yahooResponse.SearchStock;
import at.kaindorf.models.PricePointDto;
import at.kaindorf.persistence.repository.UserStockRepository;
import at.kaindorf.rest.YahooFinanceClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/*
 * Matthias Filzmaier
 * 30.03.2023
 * stocknostic
 */

@RequestScoped
public class StockDataService {

    @RestClient
    YahooFinanceClient yahooFinanceClient;

    @Inject
    UserStockRepository userStockRepository;

    @Inject
    FavouriteService favouriteService;


    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //ignore json values not used in java;


    //use search request to get list of stocks, then for every stock use method getStockPriceHistoryAndMeta with stockData request to get price points and meta data
    public List<StockDto> searchStocks(String searchString, String token){
//        List<StockDto> stockDtoList = new ArrayList<>();

        //if the search field is empty display the favourite stocks first
        if(searchString.isBlank()){
            return favouriteService.getFavouriteStockList(token);
        }

        Response response = yahooFinanceClient.search(searchString);

        try {
            //read from "quotes" from the YahooFinance response
            String responseBody = response.readEntity(String.class);
            JsonNode quotesNode = mapper.readTree(responseBody).get("quotes");

            return mapper.readValue(quotesNode.traverse(), new TypeReference<List<SearchStock>>(){}).stream()
                    .map(StockDto::new)
                    //convert to Stock object --> get history and meta data (1h minimizes json length) (1d to get price points from this day)
                    .map(stockDto -> getStockPriceHistoryAndMeta(stockDto, "1h","1d", token))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //get price points and meta data from stockData request
    public StockDto getStockPriceHistoryAndMeta(StockDto stockDto, String interval, String range, String token){
        Response response = yahooFinanceClient.stockData(stockDto.getSymbol(), interval, range);

        try {
            //get result
            String responseBody = response.readEntity(String.class);
            JsonNode rootNode = mapper.readTree(responseBody).get("chart").get("result").get(0);

            //get meta information
            JsonNode metaNode = rootNode.get("meta");

            //get currency
            JsonNode currencyNode = metaNode.get("currency");
            String currency = mapper.readValue(currencyNode.traverse(), new TypeReference<String>(){});

            JsonNode previousClosePriceNode = metaNode.get("chartPreviousClose");

            //if last previousClosePrice is not available
            Double previousClosePrice = 0.0;
            if(previousClosePriceNode != null){
                previousClosePrice = mapper.readValue(previousClosePriceNode.traverse(), new TypeReference<Double>(){});
            }

            Boolean isLiked = userStockRepository.isLiked(stockDto, token);

            return stockDto.withValues(isLiked, currency, previousClosePrice, getPricePointList(rootNode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //only get price points from stockData request (detailed view 1w, 1m...)
    public List<PricePointDto> getStockPriceHistory(String symbol, String interval, String range){
        Response response = yahooFinanceClient.stockData(symbol, interval, range);
        String responseBody = response.readEntity(String.class);

        try {
            JsonNode rootNode = mapper.readTree(responseBody).get("chart").get("result").get(0);
            return getPricePointList(rootNode);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //extracts price points out of a JsonNode from the stockData request
    private List<PricePointDto> getPricePointList(JsonNode rootNode) throws IOException {
        JsonNode timestampNode = rootNode.get("timestamp");

        if(timestampNode == null || timestampNode.isEmpty()) return new ArrayList<>(); //if there are no price points

        //convert timestamps to list of DateTime
        List<LocalDateTime> timestampList = mapper.readValue(timestampNode.traverse(), new TypeReference<List<Integer>>(){})
                .stream()
                //convert timestamps to localDateTime
                .map(integer -> LocalDateTime.ofInstant(Instant.ofEpochSecond(integer), ZoneId.of("CET")))
                .toList();

        //get close prices
        JsonNode quoteNode = rootNode.get("indicators").get("quote").get(0);
        JsonNode closeNode = quoteNode.get("close");
        //get close price list
        List<Double> closeList = mapper.readValue(closeNode.traverse(), new TypeReference<List<Double>>(){});

        //transform into list
        List<PricePointDto> pricePointDtoList = new ArrayList<>();

        for(int i = 0; i < timestampList.size(); i++){

            //eliminate missing price information
            if(closeList.get(i) == null){
                if(i == 0){
                    closeList.set(i, 0.0);
                } else{
                    closeList.set(i, closeList.get(i-1));
                }
            }
            pricePointDtoList.add(new PricePointDto(timestampList.get(i), closeList.get(i)));
        }

        return pricePointDtoList;
    }
}
