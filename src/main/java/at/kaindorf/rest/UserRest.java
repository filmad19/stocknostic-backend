package at.kaindorf.rest;

import at.kaindorf.models.Token;
import at.kaindorf.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/login")
@RequestScoped
public class UserRest {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Token login(@HeaderParam("access_token") String access_token){
        return userService.login(access_token);
    }
}
