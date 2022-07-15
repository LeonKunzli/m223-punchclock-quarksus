package ch.zli.m223.punchclock.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.AuthenticationService;
import ch.zli.m223.punchclock.service.UserService;

@Tag(name = "Authorization", description = "Sample to manage Authorization")
@Path("/auth")
public class AuthentificationController {
    /**
     * Source: https://moodle-2.zli.ch/mod/resource/view.php?id=87772
     */
    @Inject
    AuthenticationService authenticationService; 

    @Inject 
    UserService userService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(User user){
        if(!authenticationService.checkIfUserExists(user)){
            userService.createUser(user);
        }    
        return authenticationService.generateValidJwtToken(user.getUsername());
    }
}
