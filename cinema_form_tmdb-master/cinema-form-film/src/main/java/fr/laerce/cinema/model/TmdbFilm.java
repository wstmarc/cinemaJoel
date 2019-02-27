package fr.laerce.cinema.model;

import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="tmdb_films")
public class TmdbFilm {
    @Id
    @Column (name = "id", nullable = false)
    private Long id;
    @Basic
    @Column (name="original_title")
    private String original_title;
    @Basic
    @Column (name="popularity")
    private Double popularity;
    @Basic
    @Column (name="adult")
    private Boolean adult;
    @Basic
    @Column (name="video")
    private Boolean video;

    public TmdbFilm (){}
    public TmdbFilm (Long id,String original_title,Double popularity, Boolean adult,Boolean video){
        this.id = id;
        this.original_title= original_title;
        this.popularity= popularity;
        this.adult = adult;
        this.video=video;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOriginal_title() {
        return original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    public double getPopularity() {
        return popularity;
    }
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    public Boolean getAdult() {
        return adult;
    }
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    public Boolean getVideo() {
        return video;
    }
    public void setVideo(Boolean video) {
        this.video = video;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TmdbFilm tmdbFilm = (TmdbFilm) o;
        return Double.compare(tmdbFilm.popularity, popularity) == 0 &&
                Objects.equals(id, tmdbFilm.id) &&
                Objects.equals(original_title, tmdbFilm.original_title) &&
                Objects.equals(adult, tmdbFilm.adult) &&
                Objects.equals(video, tmdbFilm.video);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, original_title, popularity, adult, video);
    }

    @Override
    public String toString() {
        return "TmdbFilm{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", popularity=" + popularity +
                ", adult=" + adult +
                ", video=" + video +
                '}';
    }
}
