package com.example.sib.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "list_movie_relation")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ListMovieRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "favorites_list_id")
    @JsonbProperty("favorites_list_id")
    private Long favoritesListId;

    @Column(name = "movie_id")
    @JsonbProperty("movie_id")
    private Long movieId;

    public ListMovieRelation(Long favoritesListId, Long movieId) {
        this.favoritesListId = favoritesListId;
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMovieRelation that = (ListMovieRelation) o;
        return favoritesListId.equals(that.favoritesListId) &&
                movieId.equals(that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoritesListId, movieId);
    }
}
