create table favorites_list
(
    id   integer not null,
    name varchar(255),
    constraint pk_favorites_list primary key (id)
);

create table movie
(
    id          integer not null,
    title       varchar(255),
    poster_path varchar(255),
    overview    varchar(1023),
    constraint pk_movie primary key (id)
);

create table list_movie_relation
(
    id                integer not null,
    favorites_list_id integer not null,
    movie_id          integer not null,
    constraint pk_list_movie_relation primary key (id)
);

create sequence hibernate_sequence;