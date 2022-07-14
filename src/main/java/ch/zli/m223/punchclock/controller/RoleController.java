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

import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.service.RoleService;

@Path("/roles")
@Tag(name = "Roles", description = "Handling of roles")
public class RoleController {
    @Inject
    RoleService roleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all Roles", description = "")
    public List<Role> list() {
        return roleService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new Role", description = "The newly created role is returned. The id may not be passed.")
    public Role add(Role role) {
       return roleService.createRole(role);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Remove an Role", description = "The removed role is returned.")
    @Path("/{id}")
    public Role delete(@PathParam("id") Long id) {
       return roleService.removeRole(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update an Role", description = "The updated role not is returned.")
    public void update(Role role) {
       roleService.updateRole(role);
    }

}
