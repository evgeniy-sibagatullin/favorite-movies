package com.test.sib.rest.client;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/country")
public class CountriesResource {

    @Inject
    @RestClient
    CountriesService countriesService;

    @POST
    @Path("/ajax/getCountryData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountryData(String countryName) {
        return Response.ok(countriesService.getByName(countryName)).build();
    }
}