package com.test.sib.persistence;

import com.test.sib.model.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MoviesRepository implements PanacheRepository<Movie> {
}