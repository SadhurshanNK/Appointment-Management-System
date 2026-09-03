package com.sunrisedental.sunrisedentalserver.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("test")
public class JakartaEE11Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Sunrise Dental REST API is working!";
    }
}