create database cinemadb_v2_1
with owner postgres;

create table if not exists genres
(
  id     bigserial   not null
    constraint genre_pkey
    primary key,
  name   varchar(30) not null,
  idtmdb bigint
);

alter table genres
  owner to postgres;

create unique index if not exists genres_idtmdb_uindex
  on genres (idtmdb);

create table if not exists "user"
(
  id        bigserial    not null
    constraint user_pkey
    primary key,
  surname   varchar(40),
  givenname varchar(30),
  login     varchar(20)  not null,
  password  varchar(120) not null
);

alter table "user"
  owner to postgres;

create unique index if not exists user_login_uindex
  on "user" (login);

create table if not exists persons
(
  id         bigserial not null
    constraint persons_pkey
    primary key,
  surname    varchar(60),
  givenname  varchar(40),
  image_path varchar(80),
  birthday   date,
  idtmdb     bigint,
  name       varchar
);

alter table persons
  owner to postgres;

create unique index if not exists persons_idtmdb_uindex
  on persons (idtmdb);

create table if not exists films
(
  id            bigserial not null
    constraint films_pkey
    primary key,
  title         varchar(50),
  rating        numeric(10, 5),
  image_path    varchar(120),
  summary       text,
  film_director bigint
    constraint films_persons_id_fk
    references persons,
  release_date  date,
  idtmdb        integer
);

alter table films
  owner to postgres;

create table if not exists review
(
  id      bigserial               not null
    constraint review_pkey
    primary key,
  film_id bigint                  not null
    constraint review_films_id_fk
    references films,
  user_id bigint                  not null
    constraint review_user_id_fk
    references "user",
  article text,
  datte   timestamp default now() not null
);

alter table review
  owner to postgres;

create table if not exists play
(
  id        bigserial   not null
    constraint play_pkey
    primary key,
  film_id   bigint      not null
    constraint play_films_id_fk
    references films,
  person_id bigint      not null
    constraint play___fk_person
    references persons
    on update cascade on delete cascade,
  rank      integer     not null,
  name      varchar(50) not null
);

alter table play
  owner to postgres;

create table if not exists film_genre
(
  genre_id bigint  not null
    constraint film_genre_genre_id_fk
    references genres,
  film_id  integer not null
    constraint film_genre_films_id_fk
    references films,
  constraint film_genre_pk
  primary key (film_id, genre_id)
);

alter table film_genre
  owner to postgres;

create table if not exists tmdbfilmtitres
(
  id     bigserial not null
    constraint tmdbfilmtitres_pkey
    primary key,
  title  varchar(255),
  tmdbid bigserial not null
);

alter table tmdbfilmtitres
  owner to postgres;

create unique index if not exists tmdbfilmtitres_id_uindex
  on tmdbfilmtitres (id);

create unique index if not exists tmdbfilmtitres_tmdbid_uindex
  on tmdbfilmtitres (tmdbid);


