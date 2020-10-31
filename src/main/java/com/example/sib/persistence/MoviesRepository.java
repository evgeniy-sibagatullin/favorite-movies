package com.example.sib.persistence;

import com.example.sib.model.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MoviesRepository implements PanacheRepository<Movie> {
}
