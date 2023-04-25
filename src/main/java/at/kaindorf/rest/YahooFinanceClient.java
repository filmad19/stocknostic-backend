package at.kaindorf.rest;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "yahoo-finance-api")
public interface YahooFinanceClient {

    @GET
    @Path("/v1/finance/search")
    @Produces(MediaType.APPLICATION_JSON)
    Response search(@QueryParam("q") String searchString);

    @GET
    @Path("/v8/finance/chart/{symbol}")
    @Produces(MediaType.APPLICATION_JSON)
    Response stockData(@PathParam("symbol") String symbol,
                    @QueryParam("interval") String interval,
                    @QueryParam("range") String range);
}