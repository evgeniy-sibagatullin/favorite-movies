package com.example.sib.rest;

import com.example.sib.model.FavoritesList;
import com.example.sib.model.ListMovieRelation;
import com.example.sib.model.Movie;
import com.example.sib.persistence.FavoritesRepository;
import com.example.sib.persistence.MoviesRepository;
import com.example.sib.persistence.RelationsRepository;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/favorites")
public class FavoritesListResource {

    private static final Logger LOG = Logger.getLogger(FavoritesListResource.class);

    @Inject
    FavoritesRepository favoritesRepository;

    @Inject
    RelationsRepository relationsRepository;

    @Inject
    MoviesRepository moviesRepository;

    @ConfigProperty(name = "default.id")
    String id;

    @ConfigProperty(name = "list-movie-relation.favorites-list-id-column")
    String favoritesListIdColumn;

    @Transactional
    @GET
    @Path("/getAll/")
    public Response getAll() {
        LOG.info("");

        return Response.ok(favoritesRepository.listAll(Sort.by(id))).build();
    }

    @Transactional
    @POST
    @Path("/create/")
    public Response create(String name) {
        LOG.info(name);

        FavoritesList list = new FavoritesList();
        list.setName(name);
        favoritesRepository.persist(list);
        return Response.ok().build();
    }

    @Transactional
    @GET
    @Path("/{listId}")
    public Response getById(@PathParam Long listId) {
        LOG.info(listId);

        FavoritesList list = favoritesRepository.findById(listId);
        return Response.ok(list).build();
    }

    @Transactional
    @DELETE
    @Path("/delete/{listId}")
    public Response deleteById(@PathParam Long listId) {
        LOG.info(listId);

        FavoritesList list = favoritesRepository.findById(listId);
        favoritesRepository.delete(list);
        relationsRepository.delete(favoritesListIdColumn, listId);
        return Response.ok().build();
    }

    @Transactional
    @POST
    @Path("/addMovie/{listId}/{movieId}")
    public Response addMovie(@PathParam Long listId, @PathParam Long movieId, Movie movie) {
        LOG.info("listId: " + listId + ", movieId: " + movieId);

        ListMovieRelation newRelation = new ListMovieRelation(listId, movieId);
        List<ListMovieRelation> oldRelations = relationsRepository.find(favoritesListIdColumn, listId).list();
        boolean isToPersist = true;

        for (ListMovieRelation oldRelation : oldRelations) {
            if (oldRelation.equals(newRelation)) {
                isToPersist = false;
                break;
            }
        }

        if (isToPersist) {
            relationsRepository.persist(newRelation);

            if (moviesRepository.findById(movie.getId()) == null) {
                moviesRepository.persist(movie);
            }
        }

        return Response.ok().build();
    }

    @Transactional
    @GET
    @Path("/removeMovie/{listId}/{movieId}")
    public Response removeMovie(@PathParam Long listId, @PathParam Long movieId) {
        LOG.info("listId: " + listId + ", movieId: " + movieId);

        ListMovieRelation newRelation = new ListMovieRelation(listId, movieId);
        List<ListMovieRelation> oldRelations = relationsRepository.find(favoritesListIdColumn, listId).list();

        for (ListMovieRelation oldRelation : oldRelations) {
            if (oldRelation.equals(newRelation)) {
                relationsRepository.delete(oldRelation);
                break;
            }
        }

        return Response.ok().build();
    }
}
