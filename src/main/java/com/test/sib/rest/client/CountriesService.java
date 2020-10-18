package com.test.sib.rest.client;

import com.test.sib.rest.model.Country;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Set;

@Path("/v2")
@RegisterRestClient(configKey = "country-api")
public interface CountriesService {

    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    Set<Country> getByName(@PathParam String name);
}