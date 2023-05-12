package at.kaindorf.rest;

import at.kaindorf.models.PricePoint;
import at.kaindorf.services.IndicatorService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/indicator")
@RequestScoped
public class IndicatorRest {

    @Inject
    IndicatorService indicatorService;

    @GET
    @Path("/recommendation")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRecommendation(@QueryParam("symbol") String symbol){
        return indicatorService.getRecommendation(symbol);
    }

    @GET
    @Path("/rsi")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PricePoint> calculateRSI(@QueryParam("symbol") String symbol){
        return indicatorService.calculateRSI(symbol);
    }
}
