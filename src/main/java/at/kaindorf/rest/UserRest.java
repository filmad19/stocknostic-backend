package at.kaindorf.rest;

import at.kaindorf.models.Token;
import at.kaindorf.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@RequestScoped
public class UserRest {

    @Inject
    UserService userService;

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Token createAccessToken(){
        return userService.createAccessToken();
    }
}
