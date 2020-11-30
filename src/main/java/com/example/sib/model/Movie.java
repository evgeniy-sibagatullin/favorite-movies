package com.example.sib.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "movie")
@Getter
@Setter
@ToString
@Data
public class Movie {

    @Id
    private Long id;

    private String title;

    @Column(name = "poster_path")
    @JsonbProperty("poster_path")
    private String posterPath;

    private String overview;
}
