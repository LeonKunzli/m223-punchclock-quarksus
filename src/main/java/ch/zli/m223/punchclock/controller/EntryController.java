package ch.zli.m223.punchclock.controller;

import java.util.List;

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

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.EntryService;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.security.runtime.SecurityIdentityAssociation;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {
    @Inject
    EntryService entryService;

    @Inject
    UserService userService;

    @Inject
    SecurityIdentityAssociation identity;


    /**
       * lists all Entries
      *
      * @return list of all Entries
      */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all Entries", description = "")
    public List<Entry> list() {
      return entryService.findAll(getCurrentUser());
    }


   /**
      * creates a entry
      *
      *@param entry the entry to be created
      * @return the created entry
      */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new Entry", description = "The newly created entry is returned. The id may not be passed.")
    public Entry add(Entry entry) {
      entry.setUser(getCurrentUser());
       return entryService.createEntry(entry);
    }


   /**
      * deleted a entry
      *
      *@param id the entry to be deleted
      * @return the deleted entry
      */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove an Entry", description = "The removed entry is returned.")
    @Path("/{id}")
    public Entry delete(@PathParam("id") Long id) {
       return entryService.removeEntry(id);
    }

   /**
      * updates a entry
      *
      *@param entry the entry to be updated
      */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update an Entry", description = "The updated entry not is returned.")
    public void update(Entry entry) {
       entryService.updateEntry(entry);
    }


   /**
      * returns the currently logged in user
      *
      *@return the user that is logged in
      */
    private User getCurrentUser(){
      return userService.getUserByName(identity.getIdentity().getPrincipal().getName());
    }
}
