package at.kaindorf.rest;

import at.kaindorf.models.StockDto;
import at.kaindorf.models.PricePointDto;
import at.kaindorf.services.StockDataService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/*
 * Matthias Filzmaier
 * 24.03.2023
 * stocknostic
 */

@Path("/stock")
@RequestScoped
public class StockDataRest {

    @Inject
    StockDataService stockDataService;

    @GET
    @Path("/search")
    public List<StockDto> searchStocks(@QueryParam("q") String searchString,
                                      @HeaderParam("access_token") String token) {
        return stockDataService.searchStocks(searchString, token);
    }

    @GET
    @Path("/history/{symbol}")
    public List<PricePointDto> getPriceHistory(@PathParam("symbol") String symbol,
                                               @QueryParam("interval") String interval,
                                               @QueryParam("range") String range) {
        return stockDataService.getStockPriceHistory(symbol, interval, range);
    }
}
