package com.example.sib.persistence;

import com.example.sib.model.ListMovieRelation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RelationsRepository implements PanacheRepository<ListMovieRelation> {
}
