package com.iggy.zap.adder.resources;


import javax.ws.rs.*;

@Path("/+")
public class AddResource {

    @Consumes
    @Produces("text/plain")
    @GET
    @Path("{first}/{second}")
    public int add(@PathParam("first") int first, @PathParam("second") int second) {

        return first + second;

    }
}
