package fr.laerce.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "surname", nullable = false, length = 60)
    private String surname;
    @Basic
    @Column(name = "place_of_birth", nullable = true, length = 40)
//    private String place_of_birth;
    private String place_of_birth;//joel

    @Basic
    @Column(name = "birthday", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Basic
    @Column(name = "image_path", nullable = true, length = 80)
    private String imagePath;
    @Basic
    @Column(name = "name", nullable = true, length = 100)
    private String name;
    @Basic
    @Column(name = "idtmdb")
    private BigInteger idtmdb;

//    @NotNull
//
//    private long tmdbid;

    @OneToMany(mappedBy = "director")
    @JsonIgnore
    private Set<Film> directedFilms;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Play> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

   public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthYear) {
        this.birthday = birthYear;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<Film> getDirectedFilms() {
        return directedFilms;
    }

    public void setDirectedFilms(Set<Film> films) {
        this.directedFilms = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getIdtmdb() {
        return idtmdb;
    }

    public void setIdtmdb(BigInteger idtmdb) {
        this.idtmdb = idtmdb;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getId() == person.getId() &&
                Objects.equals(getSurname(), person.getSurname()) &&
                Objects.equals(getPlace_of_birth(), person.getPlace_of_birth()) &&
                Objects.equals(getBirthday(), person.getBirthday()) &&
                Objects.equals(getImagePath(), person.getImagePath()) &&
                Objects.equals(getName(), person.getName()) &&
                Objects.equals(getIdtmdb(), person.getIdtmdb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSurname(), getPlace_of_birth(), getBirthday(), getImagePath(), getName(), getIdtmdb());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", birthday=" + birthday +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", idtmdb=" + idtmdb +
                '}';
    }
}
