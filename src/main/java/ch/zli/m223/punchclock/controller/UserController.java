package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.security.runtime.SecurityIdentityAssociation;

@Path("/users")
@Tag(name = "Users", description = "Handling of users")
public class UserController {
    @Inject
    UserService userService;

    @Inject
    SecurityIdentityAssociation identity;

   /**
      * lists all Users
      *
      * @return list of all Users
      */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all Users", description = "")
    public List<User> list() {
        return userService.findAll();
    }


   /**
      * creates a user
      *
      *@param user the user to be created
      * @return the created user
      */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new User", description = "The newly created user is returned. The id may not be passed.")
    public User add(User user) {
       return userService.createUser(user);
    }


   /**
      * deletes a user
      *
      *@param id the user to be deleted
      * @return the deleted user
      */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove an User", description = "The removed user is returned.")
    @Path("/{id}")
    public User delete(@PathParam("id") Long id) {
       return userService.removeUser(id);
    }

   /**
      * updates a user
      *
      *@param user the uer to be updated
      */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update an User", description = "The updated user not is returned.")
    public void update(User user) {
       userService.updateUser(user);
    }

   /**
      * returns the role of the currently logged in user
      *
      *@return the role of the user that is logged in
      */
    @GET
    @Path("/currentuserrole")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserRole() {
      String name = identity.getIdentity().getPrincipal().getName();
      User user = userService.getUserByName(name);
      return user.getRoles().get(0).getRole();
    }

}
