package at.kaindorf.services;

import at.kaindorf.models.Stock;
import at.kaindorf.models.yahooResponse.SearchStock;
import at.kaindorf.models.PricePoint;
import at.kaindorf.rest.YahooFinanceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@RequestScoped
public class StockDataService {

    @RestClient
    YahooFinanceClient yahooFinanceClient;


    private static ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //ignore json values not used in java;



    public List<Stock> searchStocks(String search){
        Response response = yahooFinanceClient.search(search);
        String responseBody = response.readEntity(String.class);

        try {
            JsonNode quotesNode = mapper.readTree(responseBody).get("quotes");

            return mapper.readValue(quotesNode.traverse(), new TypeReference<List<SearchStock>>(){})
                    .stream()
//                    convert to Stock object --> get history and meta data             --> set interval and range  --> set company name from searchStock object
                    .map(searchStock -> getStockPriceHistoryAndMeta(searchStock.getSymbol(),"5m","1d").setCompanyName(searchStock.getCompanyName()))
                    .toList();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stock getStockPriceHistoryAndMeta(String symbol, String interval, String range){
        Response response = yahooFinanceClient.stockData(symbol, interval, range);
        String responseBody = response.readEntity(String.class);

        try {
            JsonNode rootNode = mapper.readTree(responseBody).get("chart").get("result").get(0);

            //get meta information
            JsonNode metaNode = rootNode.get("meta");

            JsonNode currencyNode = metaNode.get("currency");
            String currency = mapper.readValue(currencyNode.traverse(), new TypeReference<String>(){});

            JsonNode previousClosePriceNode = metaNode.get("chartPreviousClose");
            Double previousClosePrice = mapper.readValue(previousClosePriceNode.traverse(), new TypeReference<Double>(){});


            return new Stock(symbol, currency, previousClosePrice, getPricePointList(rootNode));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PricePoint> getStockPriceHistory(String symbol, String interval, String range){
        Response response = yahooFinanceClient.stockData(symbol, interval, range);
        String responseBody = response.readEntity(String.class);

        try {
            JsonNode rootNode = mapper.readTree(responseBody).get("chart").get("result").get(0);
            return getPricePointList(rootNode);

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PricePoint> getPricePointList(JsonNode rootNode) throws IOException {
        JsonNode timestampNode = rootNode.get("timestamp");

        //convert timestamps to list of DateTime
        List<LocalDateTime> timestampList = mapper.readValue(timestampNode.traverse(), new TypeReference<List<Integer>>(){})
                .stream()
                .map(integer -> LocalDateTime.ofInstant(Instant.ofEpochSecond(integer), ZoneId.of("CET")))
                .toList();

        //get close prices
        JsonNode quoteNode = rootNode.get("indicators").get("quote").get(0);
        JsonNode closeNode = quoteNode.get("close");
        List<Double> closeList = mapper.readValue(closeNode.traverse(), new TypeReference<List<Double>>(){});

        //transform into list
        List<PricePoint> pricePointList = new ArrayList<>();

        for(int i = 0; i < timestampList.size(); i++){
            pricePointList.add(new PricePoint(timestampList.get(i), closeList.get(i)));
        }

//            JsonNode openNode = quoteNode.get("open");
//            JsonNode lowNode = quoteNode.get("low");
//            JsonNode highNode = quoteNode.get("high");
//            JsonNode volumeNode = quoteNode.get("volume");

        return pricePointList;
    }
}
