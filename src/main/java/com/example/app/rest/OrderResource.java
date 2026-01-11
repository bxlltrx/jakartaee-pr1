package com.example.app.rest;

import com.example.app.ejb.OrderService;
import com.example.app.entity.PurchaseOrder;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @EJB
    private OrderService orderService;

    public static class CreateOrderRequest {
        public Long userId;
        public BigDecimal totalAmount;
    }

    @GET
    public List<PurchaseOrder> all() {
        return orderService.findAll();
    }

    @POST
    public Response create(CreateOrderRequest req) {
        if (req == null || req.userId == null || req.totalAmount == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("userId/totalAmount required").build();
        }
        PurchaseOrder created = orderService.create(req.userId, req.totalAmount);
        if (created == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("user not found").build();
        }
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean ok = orderService.delete(id);
        return ok ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
