package com.example.sib.rest;

import com.example.sib.model.MoviesPage;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/3")
@RegisterRestClient(configKey = "movie-api")
public interface MoviesService {

    @GET
    @Path("/search/movie")
    @Produces("application/json")
    MoviesPage getByQuery(@QueryParam("api_key") String apikey,
                          @QueryParam("query") String query,
                          @QueryParam("page") String page);
}
