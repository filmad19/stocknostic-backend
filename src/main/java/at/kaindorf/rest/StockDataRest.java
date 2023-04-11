package at.kaindorf.rest;

import at.kaindorf.persistence.entity.Stock;
import at.kaindorf.persistence.repository.StockRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/stock")
@RequestScoped
public class StockDataRest {

    @Inject
    StockRepository stockRepository;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "STOCK GETTING";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Stock getStockById(@PathParam("id") ObjectId id) {
        return stockRepository.findStockById(id).orElseThrow();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<Stock> getAllStock() {
        return stockRepository.listAll();
    }

    @POST
    public Response addStock(Stock stock, @Context UriInfo uriInfo) {
        stockRepository.addStock(stock);
        URI uri = uriInfo.getAbsolutePathBuilder().path(stock.getId().toString()).build(); //GET URL IN RESPONSE HEADER
        return Response.created(uri).build();
    }
}
