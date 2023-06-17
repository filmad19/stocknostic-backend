package at.kaindorf.rest;

import at.kaindorf.models.PricePointDto;
import at.kaindorf.models.RsiSettingsDto;
import at.kaindorf.services.IndicatorService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * Matthias Filzmaier
 * 06.05.2023
 * stocknostic
 */

@Path("/indicator")
@RequestScoped
public class IndicatorRest {

    @Inject
    IndicatorService indicatorService;

    @GET
    @Path("/rsi")
    @Produces(MediaType.APPLICATION_JSON)
    public PricePointDto calculateRSI(@QueryParam("symbol") String symbol){
        return indicatorService.calculateRSI(symbol);
    }

    @POST
    @Path("/rsi/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setRsiSettings(@HeaderParam("access_token") String token, @QueryParam("symbol") String symbol, RsiSettingsDto settingsDto){
        indicatorService.setRsiSettings(token, settingsDto, symbol);
        return Response.ok().build();
    }

    @GET
    @Path("/rsi/settings")
    @Produces(MediaType.APPLICATION_JSON)
    public RsiSettingsDto getRsiSettings(@HeaderParam("access_token") String token, @QueryParam("symbol") String symbol){
        return indicatorService.getRsiSettings(token, symbol);
    }
}
