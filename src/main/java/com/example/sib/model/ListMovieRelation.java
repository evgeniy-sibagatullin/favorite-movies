package com.example.sib.model;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "list_movie_relation")
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
}
