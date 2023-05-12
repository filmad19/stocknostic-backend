package at.kaindorf.rest;

import at.kaindorf.models.Stock;
import at.kaindorf.services.FavouriteService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/favourite")
@RequestScoped
public class FavouriteRest {

    @Inject
    FavouriteService favouriteService;

    @GET
    public List<Stock> getFavouriteStockList(@HeaderParam("access_token") String token){
        return favouriteService.getFavouriteStockList(token);
    }

    @POST
    public Response addStockToFavourite(@HeaderParam("access_token") String token, String symbol){
        favouriteService.addStockToFavourite(symbol, token);
        return Response.ok().build();
    }

    @DELETE
    public Response removeStockFromFavourite(@HeaderParam("access_token") String token, @QueryParam("symbol") String symbol){
        favouriteService.removeStockFromFavourite(symbol, token);
        return Response.accepted().build();
    }
}
