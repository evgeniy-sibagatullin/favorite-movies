package com.test.sib.rest;

import com.test.sib.model.ListMovieRelation;
import com.test.sib.model.Movie;
import com.test.sib.model.MoviesPage;
import com.test.sib.persistence.MoviesRepository;
import com.test.sib.persistence.RelationsRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/movie")
public class MoviesResource {

    private static final String API_KEY = "0f9b4ab5e8db77aca1b01cb2d7f892e6";
    private static final String PAGE_1 = "1";

    @Inject
    @RestClient
    MoviesService moviesService;

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    RelationsRepository relationsRepository;

    @POST
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(String query) {
        query = query.replaceAll("[^a-zA-Z0-9]", "");
        MoviesPage page = moviesService.getByQuery(API_KEY, query, PAGE_1);
        return Response.ok(page.results).build();
    }

    @Transactional
    @POST
    @Path("/getDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetails(Movie movie) {
        if (moviesRepository.findById(movie.getId()) == null) {
            moviesRepository.persist(movie);
        }

        return Response.ok().build();
    }

    @Transactional
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam String id) {
        Movie movie = moviesRepository.findById(Long.valueOf(id));

        if (movie != null) {
            return Response.ok(movie).build();
        } else {
            return Response.serverError().build();
        }
    }

    @Transactional
    @GET
    @Path("/getMovies/{listId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(@PathParam Long listId) {
        List<ListMovieRelation> relations = relationsRepository.find("favorites_list_id", listId).list();
        List<Movie> movies = new ArrayList<>();

        for (ListMovieRelation relation : relations) {
            movies.add(moviesRepository.findById(relation.getMovie_id()));
        }

        return Response.ok(movies).build();
    }
}
