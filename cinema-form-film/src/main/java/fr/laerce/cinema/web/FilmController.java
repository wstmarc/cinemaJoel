package fr.laerce.cinema.web;

import fr.laerce.cinema.dao.*;
import fr.laerce.cinema.model.Film;
import fr.laerce.cinema.model.Play;
import fr.laerce.cinema.model.TmdbFilm;
import fr.laerce.cinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@Controller
@RequestMapping("/film")
public class FilmController {

    @Autowired
    TmdbClient tmdbClient;
    @Autowired
    ImportFilmsFromTMDB importFilmsFromTMDB;
    @Autowired
    FilmManager filmManager;

    @Autowired
    PersonManager personManager;

    @Autowired
    GenreManager genreManager;

    @Autowired
    TmdbFilmDao tmdbFilmDao;
    @Autowired
    RoleDao roleDao;




    @Autowired
    ImageManager imm;

    @GetMapping("/list")
    public String list(Model model) {
        Iterable<Film> films = filmManager.getAll();
        model.addAttribute("films", films);
        return "film/list";
    }
//    @GetMapping("/tmdb/{idtmdb}")
//    public String tmdbfilm(@PathVariable("idtmdb")BigInteger idtmdb, RedirectAttributes redirectAttrs) throws Exception {
//    tmdbClient.getMovieByTmdbId(idtmdb);
//    redirectAttrs.addAttribute("message","film ajouter !!!");
//    long id = filmManager.findByIdTmdb(idtmdb).getId();
//    return "redirect:/film/detail/"+id;
//    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
        model.addAttribute("film", filmManager.getById(id));
        model.addAttribute("roles", roleDao.findByFilm_IdOrderByRankAsc(id));
        return "film/detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("title", "Ajout d'un film");
        Film film = new Film();
        model.addAttribute("film", film);
        model.addAttribute("persons", personManager.getAll());
        model.addAttribute("plays", film.getRoles());
        model.addAttribute("newrole", new Play());
        model.addAttribute("genresFilm", genreManager.getAll());
        return "film/form";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        filmManager.delete(id);
        return "film/delete";
    }

    @GetMapping("/mod/{id}")
    public String mod(@PathVariable("id") long id, Model model) {
        Film film = filmManager.getById(id);
        model.addAttribute("title", film.getTitle() + " : modification");
        model.addAttribute("persons", personManager.getAll());
        model.addAttribute("genresFilm", genreManager.getAll());
        model.addAttribute("film", film);
        model.addAttribute("plays", film.getRoles());
        model.addAttribute("newrole", new Play());
        return "film/form";
    }


    @PostMapping("/add")
    public String submit(@ModelAttribute Film film) {
        filmManager.save(film);
        return "redirect:list";
    }

    @GetMapping("/rmrole/{role_id}")
    public String rmRole(@PathVariable("role_id") Long roleId) {
        long filmId = filmManager.removeRole(roleId);

        return "redirect:/film/mod/" + filmId;
    }

    @PostMapping("/addrole")
    public String addRole(@ModelAttribute Play role) {
        long filmId = role.getFilm().getId();
        filmManager.addRole(filmId, role);
        return "redirect:/film/mod/" + filmId;
    }

    @PostMapping("/modrole/{id}")
    public String modRole(@PathVariable("id") long roleId, @ModelAttribute Play role) {
        filmManager.saveRole(role);
        return "redirect:/film/mod/" + role.getFilm().getId();
    }


}
