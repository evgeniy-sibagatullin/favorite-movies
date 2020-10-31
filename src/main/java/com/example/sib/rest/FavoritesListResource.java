package com.example.sib.rest;

import com.example.sib.model.FavoritesList;
import com.example.sib.model.ListMovieRelation;
import com.example.sib.model.Movie;
import com.example.sib.persistence.FavoritesRepository;
import com.example.sib.persistence.MoviesRepository;
import com.example.sib.persistence.RelationsRepository;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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
        return Response.ok(favoritesRepository.listAll(Sort.by(id))).build();
    }

    @Transactional
    @POST
    @Path("/create/")
    public Response create(String name) {
        FavoritesList list = new FavoritesList();
        list.setName(name);
        favoritesRepository.persist(list);
        return Response.ok().build();
    }

    @Transactional
    @GET
    @Path("/{id}")
    public Response getById(@PathParam String id) {
        FavoritesList list = favoritesRepository.findById(Long.valueOf(id));
        return Response.ok(list).build();
    }

    @Transactional
    @DELETE
    @Path("/delete/{id}")
    public Response deleteById(@PathParam Long id) {
        FavoritesList list = favoritesRepository.findById(id);
        favoritesRepository.delete(list);
        relationsRepository.delete(favoritesListIdColumn, id);
        return Response.ok().build();
    }

    @Transactional
    @POST
    @Path("/addMovie/{favoritesListId}/{movieId}")
    public Response addMovie(@PathParam Long favoritesListId, @PathParam Long movieId, Movie movie) {
        ListMovieRelation newRelation = new ListMovieRelation(favoritesListId, movieId);
        List<ListMovieRelation> oldRelations = relationsRepository.find(favoritesListIdColumn, favoritesListId).list();
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
    @Path("/removeMovie/{favoritesListId}/{movieId}")
    public Response removeMovie(@PathParam Long favoritesListId, @PathParam Long movieId) {
        ListMovieRelation newRelation = new ListMovieRelation(favoritesListId, movieId);
        List<ListMovieRelation> oldRelations = relationsRepository.find(favoritesListIdColumn, favoritesListId).list();

        for (ListMovieRelation oldRelation : oldRelations) {
            if (oldRelation.equals(newRelation)) {
                relationsRepository.delete(oldRelation);
                break;
            }
        }

        return Response.ok().build();
    }
}
