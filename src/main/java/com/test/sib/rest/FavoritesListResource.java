package com.test.sib.rest;

import com.test.sib.model.FavoritesList;
import com.test.sib.model.ListMovieRelation;
import com.test.sib.model.Movie;
import com.test.sib.persistence.FavoritesRepository;
import com.test.sib.persistence.MoviesRepository;
import com.test.sib.persistence.RelationsRepository;
import io.quarkus.panache.common.Sort;
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

    @Transactional
    @GET
    @Path("/getAll/")
    public Response getAll() {
        return Response.ok(favoritesRepository.listAll(Sort.by("id"))).build();
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
    public Response deleteById(@PathParam String id) {
        FavoritesList list = favoritesRepository.findById(Long.valueOf(id));
        favoritesRepository.delete(list);
        return Response.ok().build();
    }

    @Transactional
    @POST
    @Path("/addMovie/{listId}/{movieId}")
    public Response addMovie(@PathParam String listId, @PathParam String movieId, Movie movie) {
        Long favoritesListId = Long.valueOf(listId);
        ListMovieRelation newRelation = new ListMovieRelation(favoritesListId, Long.valueOf(movieId));
        List<ListMovieRelation> oldRelations = relationsRepository.find("favorites_list_id", favoritesListId).list();
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
}
