package at.kaindorf.rest;

import at.kaindorf.models.StockDto;
import at.kaindorf.services.FavouriteService;
import io.quarkus.security.UnauthorizedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/favourite")
@RequestScoped
public class FavouriteRest {

    @Inject
    FavouriteService favouriteService;

    @POST
    public Response addStockToFavourite(@HeaderParam("access_token") String token, StockDto stock){
        try {
            favouriteService.addStockToFavourite(stock, token);
            return Response.ok().build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    public Response removeStockFromFavourite(@HeaderParam("access_token") String token, @QueryParam("symbol") String symbol){
        try {
            favouriteService.removeStockFromFavourite(symbol, token);
            return Response.accepted().build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
