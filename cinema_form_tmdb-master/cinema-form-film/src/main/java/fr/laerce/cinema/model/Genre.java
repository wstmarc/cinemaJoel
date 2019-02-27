package fr.laerce.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

/**
 * Entité pour les Genres des films
 */
@Entity
@Table(name = "genres")
public class Genre {
    /**
     * L'identifiant unique dans la base
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Le nom du genre
     */
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic
    @Column(name = "idtmdb")
    private BigInteger idtmdb;

    public BigInteger getIdtmdb() {
        return idtmdb;
    }

    public void setIdtmdb(BigInteger idtmdb) {
        this.idtmdb = idtmdb;
    }

    /**
     * L'ensemble des films associés au genre
     */
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Film> films;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getId(), genre.getId()) &&
                Objects.equals(getName(), genre.getName()) &&
                Objects.equals(getIdtmdb(), genre.getIdtmdb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getIdtmdb());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idtmdb=" + idtmdb +
                '}';
    }
}
