package com.test.sib.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "list_movie_relation")
public class ListMovieRelation {

    @Id
    @GeneratedValue
    private Long id;

    private Long favorites_list_id;

    private Long movie_id;

    public ListMovieRelation() {
    }

    public ListMovieRelation(Long favorites_list_id, Long movie_id) {
        this.favorites_list_id = favorites_list_id;
        this.movie_id = movie_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFavorites_list_id() {
        return favorites_list_id;
    }

    public void setFavorites_list_id(Long favorites_list_id) {
        this.favorites_list_id = favorites_list_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMovieRelation that = (ListMovieRelation) o;
        return favorites_list_id.equals(that.favorites_list_id) &&
                movie_id.equals(that.movie_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorites_list_id, movie_id);
    }

    @Override
    public String toString() {
        return "ListMovieRelation{" +
                "id=" + id +
                ", favorites_list_id=" + favorites_list_id +
                ", movie_id=" + movie_id +
                '}';
    }
}
