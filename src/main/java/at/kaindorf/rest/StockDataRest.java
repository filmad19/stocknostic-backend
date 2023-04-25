package at.kaindorf.rest;

import at.kaindorf.models.Stock;
import at.kaindorf.models.PricePoint;
import at.kaindorf.services.StockDataService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/stock")
@RequestScoped
public class StockDataRest {

    @Inject
    StockDataService stockDataService;

    @GET
    @Path("/search")
    public List<Stock> searchStocks(@QueryParam("q") String searchString) {
        return stockDataService.searchStocks(searchString);
    }

    @GET
    @Path("/{symbol}")
    public Stock getStockAndPriceHistory(@PathParam("symbol") String symbol,
                                         @QueryParam("interval") String interval,
                                         @QueryParam("range") String range) {
        return stockDataService.getStockPriceHistoryAndMeta(symbol, interval, range);
    }

    @GET
    @Path("/history/{symbol}")
    public List<PricePoint> getPriceHistory(@PathParam("symbol") String symbol,
                                            @QueryParam("interval") String interval,
                                            @QueryParam("range") String range) {
        return stockDataService.getStockPriceHistory(symbol, interval, range);
    }
}
