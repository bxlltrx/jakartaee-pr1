package com.example.app.rest;

import com.example.app.ejb.UserService;
import com.example.app.entity.User;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserService userService;

    public static class CreateUserRequest {
        public String fullName;
        public String email;
    }

    @GET
    public List<User> all() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response one(@PathParam("id") Long id) {
        User u = userService.find(id);
        if (u == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(u).build();
    }

    @POST
    public Response create(CreateUserRequest req) {
        if (req == null || req.fullName == null || req.email == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("fullName/email required").build();
        }
        User created = userService.create(req.fullName, req.email);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CreateUserRequest req) {
        if (req == null || req.fullName == null || req.email == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("fullName/email required").build();
        }
        User updated = userService.update(id, req.fullName, req.email);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean ok = userService.delete(id);
        return ok ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
