package fr.laerce.cinema.model;

import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tmdbfilm")
public class TmdbFilm {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="title")
    @NotNull
    private String title;
    @Column(name="tmdbid")
    @NotNull
    private long tmdbid;

    public TmdbFilm(){

    }

    public TmdbFilm(@NotNull String title, @NotNull @Unique long tmdbid) {
        this.title = title;
        this.tmdbid = tmdbid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTmdbid() {
        return tmdbid;
    }

    public void setTmdbid(long tmdbid) {
        this.tmdbid = tmdbid;
    }
}