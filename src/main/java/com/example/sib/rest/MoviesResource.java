package com.example.sib.rest;

import com.example.sib.model.FavoritesList;
import com.example.sib.model.ListMovieRelation;
import com.example.sib.model.Movie;
import com.example.sib.model.MoviesPage;
import com.example.sib.persistence.FavoritesRepository;
import com.example.sib.persistence.MoviesRepository;
import com.example.sib.persistence.RelationsRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
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

    private static final Logger LOG = Logger.getLogger(MoviesResource.class);

    @Inject
    @RestClient
    MoviesService moviesService;

    @Inject
    MoviesRepository moviesRepository;

    @Inject
    RelationsRepository relationsRepository;

    @Inject
    FavoritesRepository favoritesRepository;

    @ConfigProperty(name = "movie-api.api-key")
    String apiKey;

    @ConfigProperty(name = "movie-api.default-page")
    String defaultPage;

    @ConfigProperty(name = "list-movie-relation.favorites-list-id-column")
    String favoritesListIdColumn;

    @ConfigProperty(name = "list-movie-relation.movie-id-column")
    String movieIdColumn;

    @POST
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(String query) {
        LOG.info(query);

        query = query.replaceAll("[^a-zA-Z0-9]", "");
        MoviesPage page = moviesService.getByQuery(apiKey, query, defaultPage);
        return Response.ok(page.results).build();
    }

    @Transactional
    @POST
    @Path("/getDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetails(Movie movie) {
        LOG.info(movie.getId());

        if (moviesRepository.findById(movie.getId()) == null) {
            moviesRepository.persist(movie);
        }

        return Response.ok().build();
    }

    @Transactional
    @GET
    @Path("/{movieId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam Long movieId) {
        LOG.info(movieId);

        Movie movie = moviesRepository.findById(movieId);

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
        LOG.info(listId);

        List<ListMovieRelation> relations = relationsRepository.find(favoritesListIdColumn, listId).list();
        List<Movie> movies = new ArrayList<>();

        for (ListMovieRelation relation : relations) {
            movies.add(moviesRepository.findById(relation.getMovie_id()));
        }

        return Response.ok(movies).build();
    }


    @Transactional
    @GET
    @Path("/getFavoritesLists/{movieId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavoritesLists(@PathParam Long movieId) {
        LOG.info(movieId);

        List<ListMovieRelation> relations = relationsRepository.find(movieIdColumn, movieId).list();
        List<FavoritesList> favoritesLists = new ArrayList<>();

        for (ListMovieRelation relation : relations) {
            favoritesLists.add(favoritesRepository.findById(relation.getFavorites_list_id()));
        }

        return Response.ok(favoritesLists).build();
    }
}
