package fr.laerce.cinema.api;

import fr.laerce.cinema.model.Film;
import fr.laerce.cinema.service.FilmManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/film")
public class FilmRestController {
    private FilmManager filmManager;

    public FilmRestController(FilmManager filmManager) {
        assert (filmManager != null);
        this.filmManager = filmManager;
    }


    @GetMapping("")
    public List<Film> getAll() {
        return filmManager.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable("id") long id) {
        return filmManager.getById(id);
    }


    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Film remove(@PathVariable("id") long id) {
        return filmManager.delete(id);
    }


}
