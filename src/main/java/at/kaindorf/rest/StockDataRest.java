package at.kaindorf.rest;

import at.kaindorf.models.SymbolSearchResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Path("/stock")
@RequestScoped
public class StockDataRest {

    @RestClient
    AlphaVantageClient alphaVantageClient;

//    @GET
//    @Path("/{symbol}")
//    public List<HistoricalQuote> searchStock(@PathParam("symbol") String symbol) throws IOException {
//        symbol = "AAPL";
//
//        Stock stock = YahooFinance.get(symbol);
//
//        BigDecimal price = stock.getQuote().getPrice();
//        BigDecimal change = stock.getQuote().getChangeInPercent();
//        BigDecimal peg = stock.getStats().getPeg();
//        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
//
//        return stock.getHistory(Interval.DAILY); // single request
//    }

    @GET
    @Path("/{symbol}")
    public List<HistoricalQuote> getStockData(@PathParam("symbol") String symbol) throws IOException {
        symbol = "AAPL";

        Stock stock = YahooFinance.get(symbol);

        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        LocalDate localDate = LocalDate.now().minusWeeks(1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());

        return stock.getHistory(calendar, Interval.DAILY); // single request
    }

//    @GET
//    @Path("/{symbol}")
//    public List<HistoricalQuote> etStockSearch(@PathParam("symbol") String symbol) throws IOException {
//        SymbolSearchResponse response = alphaVantageClient.symbolSearch("SYMBOL_SEARCH", symbol, "2KCJ7U4RAU02S6TE");
//        System.out.println(response);
//        return response.getStocks();
//    }

//    @GET
//    @Path("/{symbol}")
//    public List<StockListItem> getStockData(@PathParam("symbol") String symbol) {
//        SymbolSearchResponse response = alphaVantageClient.getStockData("TIME_SERIES_DAILY_ADJUSTED", symbol, "5min","2KCJ7U4RAU02S6TE");
//        System.out.println(response);
//
//        return response.getStocks();
//    }

//    @GET
//    @Path("/symbols")
//    public Response getStockSymbols() {
//        return alphaVantageClient.getStockSymbols("LISTING_STATUS", "2KCJ7U4RAU02S6TE");
//    }

//    @GET
//    public List<Stock> getAllStock() {
//        return stockRepository.listAll();
//    }
//
//    @POST
//    public Response addStock(Stock stock, @Context UriInfo uriInfo) {
//        stockRepository.addStock(stock);
//        URI uri = uriInfo.getAbsolutePathBuilder().path(stock.getId().toString()).build(); //GET URL IN RESPONSE HEADER
//        return Response.created(uri).build();
//    }
}
