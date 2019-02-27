package fr.laerce.cinema.api;

import fr.laerce.cinema.model.Genre;
import fr.laerce.cinema.service.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/api/genre")
public class GenreRestController {

    private GenreManager genreManager;

    /**
     * Constructeur permettant l'injection de beans
     * @param genreManager le service de gestion du Genre du film
     */
    public GenreRestController(GenreManager genreManager){
        this.genreManager=genreManager;
        assert(genreManager != null);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Genre get(@PathVariable("id") long id){
        return genreManager.getById(id);
    }

    /**
     *
     * @return
     */
    @GetMapping("")
    public List<Genre> getAll() {
        return genreManager.getAll();
    }

    /**
     *
     * @param genre
     * @return
     */
    @PostMapping("")
    public Genre add(@RequestBody Genre genre){
        if (genre.getName().isEmpty()) throw new IllegalArgumentException("Le champs Nom est vide!!!") ;
        if (genre.getName().length() <= 2 ) throw  new IllegalArgumentException("la longueur du nom minimum est de 2");
        if (genre.getName().length() >=30 ) throw  new IllegalArgumentException("la longueur du nom maximum est de 30");

        return genreManager.save(genre);
    }

    /**
     *
     * @param genre
     * @return
     */
    @PutMapping("")
    public Genre mod(@RequestBody Genre genre){
        return genreManager.save(genre);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Genre remove(@PathVariable("id") long id){
        return genreManager.delete(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/rm/{id}")
    public Genre rm(@PathVariable("id")long id){

        return genreManager.delete(id);
    }
}
