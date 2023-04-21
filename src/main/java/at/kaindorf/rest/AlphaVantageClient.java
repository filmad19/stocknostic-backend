package at.kaindorf.rest;


import at.kaindorf.models.SymbolSearchResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/query")
@RegisterRestClient(configKey = "alpha-vantage-api")
public interface AlphaVantageClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getStockData(@QueryParam("function") String function,
                                      @QueryParam("symbol") String symbol,
                                      @QueryParam("interval") String interval,
                                         @QueryParam("apikey") String apiKey);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    SymbolSearchResponse symbolSearch(@QueryParam("function") String function,
                                      @QueryParam("keywords") String symbol,
                                      @QueryParam("apikey") String apiKey);

    @GET    @Produces(MediaType.APPLICATION_JSON)
    Response getStockSymbols(@QueryParam("function") String function,
                             @QueryParam("apikey") String apiKey);
}
