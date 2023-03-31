package at.kaindorf.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stock")
@RequestScoped
public class StockDataRest {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "STOCK GETTING";
    }
}
