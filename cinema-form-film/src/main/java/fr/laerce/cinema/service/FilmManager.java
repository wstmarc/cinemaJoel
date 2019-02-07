package fr.laerce.cinema.service;

import fr.laerce.cinema.dao.FilmDao;
import fr.laerce.cinema.dao.RoleDao;
import fr.laerce.cinema.model.Film;
import fr.laerce.cinema.model.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.List;

@Component
public class FilmManager {
    @Autowired
    private FilmDao filmDao;

    @Autowired
    private RoleDao roleDao;

    public Film getById(long id){
        return filmDao.findById(id).get();
    }
    public boolean existsByIdtmbd(BigInteger id){
        return filmDao.existsByIdtmbd(id);
    }

    public List<Film> getAll(){
        return filmDao.findAllByOrderByTitle();
    }

    /**
     * Sauvegarder le film
     * @param film le film à créer ou modifier
     * @return l'id du film créé ou modifié
     */
    public Long save(Film film){
        filmDao.save(film);
        return film.getId();
    }
    public Film saveFilm(Film f)
    {
        return filmDao.save(f);
    }
//   public Film save(Film film){
//       if (filmDao.findByTitle(film.getTitle()).getTitle().isEmpty()){
//            throw new IllegalArgumentException("le champs titre est vide    ");
//        }
//        return filmDao.save(film);
//        try{
//            film = filmDao.save(film);
//        }catch (ConstraintViolationException cve){
//           throw new IllegalArgumentException("Le genre "+film.()+"existe deja");
//       }
//        return genre;


    /**
     * Supprime un rôle dans un film
     * @param roleId l'identifiant du rôle à supprimer
     * @return l'id du film auquel appartenait le rôle
     */

    public long removeRole(long roleId){
        Play role = roleDao.findById(roleId).get();
        long filmId = role.getFilm().getId();
        Film film = filmDao.findById(filmId).get();
        film.getRoles().remove(role);
        filmDao.save(film);
        roleDao.delete(role);
        return filmId;
    }

    /**
     * Crée un role associé à un film
     * @param filmId l'identifiant du film
     * @param play le role à créer
     * @return l'id du film associé au rôle
     */
    public long addRole(long filmId, Play play){
        Film film = filmDao.findById(filmId).get();
        play.setFilm(film);
        roleDao.save(play);
        return play.getFilm().getId();
    }

    public long saveRole(Play play){
        roleDao.save(play);
        return play.getId();
    }
    public Film delete(long id){
        Film film = getById(id);
        filmDao.deleteById(id);
        return film;

    }
     public Film findByIdTmdb(BigInteger id){
        return filmDao.findByIdtmbd(id);
     }
}
