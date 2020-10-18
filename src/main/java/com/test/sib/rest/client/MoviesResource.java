package com.test.sib.rest.client;

import com.test.sib.rest.model.MoviesPage;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/movie")
public class MoviesResource {

    private static final String API_KEY = "0f9b4ab5e8db77aca1b01cb2d7f892e6";
    private static final String PAGE_1 = "1";

    @Inject
    @RestClient
    MoviesService moviesService;

    @POST
    @Path("/ajax/getMovieData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieData(String query) {
        query = query.replaceAll("[^a-zA-Z0-9]", "");
        MoviesPage page = moviesService.getByQuery(API_KEY, query, PAGE_1);
        return Response.ok(page.results).build();
    }
}
