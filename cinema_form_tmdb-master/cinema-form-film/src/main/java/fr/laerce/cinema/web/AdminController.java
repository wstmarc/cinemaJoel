package fr.laerce.cinema.web;

import fr.laerce.cinema.dao.TmdbFilmDao;
import fr.laerce.cinema.service.ImportFilmsFromTMDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {
    @Autowired
    private TmdbFilmDao tmdbFilmDao;

    @Autowired
    private ImportFilmsFromTMDB importFilmsFromTMDB;

    @GetMapping("/populate")
    public String populate(Model model) {

        model.addAttribute("nom", "Joel");

//        ImportFilmsFromTMDB importFilmsFromTMDB = new ImportFilmsFromTMDB(tmdbFilmDao);
//        importFilmsFromTMDB.ImportMoviesViaOnlineTmdbFile();
        importFilmsFromTMDB.ImportMoviesViaLocalTempTmdbFile();

        //TODO: devra plus tard retourner une interface d'administration plutôt que l'index
        return "index";
    }
}

