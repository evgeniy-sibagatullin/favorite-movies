package com.test.sib.rest;

import com.test.sib.model.FavoritesList;
import com.test.sib.persistence.FavoritesRepository;
import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/favorites")
public class FavoritesListResource {

    @Inject
    FavoritesRepository favoritesRepository;

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
}
