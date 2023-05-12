package at.kaindorf.rest;

import at.kaindorf.models.Stock;
import at.kaindorf.models.PricePoint;
import at.kaindorf.services.FavouriteService;
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

    @Inject
    FavouriteService favouriteService;

    @GET
    @Path("/search")
    public List<Stock> searchStocks(@QueryParam("q") String searchString,
                                    @HeaderParam("access_token") String token) {

        if(searchString.length() == 0) return favouriteService.getFavouriteStockList(token);

        return stockDataService.searchStocks(searchString, token);
    }

    @GET
    @Path("/{symbol}")
    public Stock getStockAndPriceHistory(@PathParam("symbol") String symbol,
                                         @QueryParam("interval") String interval,
                                         @QueryParam("range") String range,
                                         @HeaderParam("access_token") String token) {
        return stockDataService.getStockPriceHistoryAndMeta(symbol, interval, range, token);
    }

    @GET
    @Path("/history/{symbol}")
    public List<PricePoint> getPriceHistory(@PathParam("symbol") String symbol,
                                            @QueryParam("interval") String interval,
                                            @QueryParam("range") String range) {
        return stockDataService.getStockPriceHistory(symbol, interval, range);
    }
}
