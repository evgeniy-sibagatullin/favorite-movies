package com.example.sib.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "favorites_list")
@Getter
@Setter
@ToString
public class FavoritesList {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
