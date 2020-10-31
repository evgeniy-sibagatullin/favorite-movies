package com.example.sib.persistence;

import com.example.sib.model.FavoritesList;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FavoritesRepository implements PanacheRepository<FavoritesList> {
}
