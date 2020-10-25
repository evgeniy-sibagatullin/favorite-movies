package com.test.sib.persistence;

import com.test.sib.model.ListMovieRelation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RelationsRepository implements PanacheRepository<ListMovieRelation> {
}
