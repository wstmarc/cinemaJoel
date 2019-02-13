package fr.laerce.cinema.service;

import fr.laerce.cinema.dao.RoleDao;
import fr.laerce.cinema.model.Film;
import fr.laerce.cinema.model.Person;
import fr.laerce.cinema.model.Play;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;



@Component
public class TmdbClient {

    @Value("${tmdb.api.key}")
    private String apiKey;
    @Autowired
    ImageManager imageManager;
    @Autowired
    FilmManager filmManager;
    @Autowired
    PersonManager personManager;
    @Autowired
    RoleDao roleDao;


    private long secondsBeforeReset(String value){
        long timestamp = Long.valueOf(stripBraces(value));
        LocalDateTime resetTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        return now.until( resetTime, ChronoUnit.SECONDS);
    }

    private String stripBraces(String value){
        return value.substring(0, value.length()-1).substring(1);
    }

    public void getMovieByTmdbId(BigInteger id) throws Exception {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;
        long reset;

        String resourceUrl = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+apiKey+"&language=fr-FR";
        response = template.getForEntity(resourceUrl, String.class);
        System.out.println(response.getBody());
        Film filmtest = new Film();
        JSONObject film = new JSONObject(response.getBody());
        JSONArray genres = (JSONArray) film.get("genres");
        filmtest.setTitle(film.getString("title"));
        filmtest.setRating(film.getDouble("popularity"));
        String url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"+film.getString("poster_path");
        InputStream image =  new URL(url).openStream();
        imageManager.savePoster(filmtest, image);
        filmtest.setSummary(film.getString("overview"));
        filmtest.setReleaseDate(LocalDate.parse(film.getString("release_date")));
        filmtest.setIdtmbd(new BigInteger(film.getString("id")));
        boolean adult = film.getBoolean("adult");
        if(!filmManager.existsByIdtmbd(filmtest.getIdtmbd()) && !adult){
            filmtest = filmManager.saveFilm(filmtest);
        }
        else {
            filmtest=filmManager.findByIdTmdb(filmtest.getIdtmbd());
        }


//        System.out.println("Titre : "+film.getString("title"));


        for(int i = 0; i < genres.length(); i++){
            JSONObject genre = (JSONObject) genres.get(i);
            System.out.println("- Genre : "+genre.getString("name"));
        }
        System.out.println("--------\nRequetes restantes : "+stripBraces(response.getHeaders().get("x-ratelimit-remaining").toString()));
        reset = secondsBeforeReset(response.getHeaders().get("x-ratelimit-reset").toString());
        System.out.println("Temps restant avant reset : "+reset+"\n\n");


        String resourceCredit = "https://api.themoviedb.org/3/movie/"+id+"/credits?api_key="+apiKey+"&language=fr-FR";
        response = template.getForEntity(resourceCredit, String.class);
        JSONObject credit = new JSONObject(response.getBody());
        JSONArray crew = (JSONArray) credit.get("crew");
        for (int e = 0; e < crew.length(); e++ )
        {
            JSONObject job = (JSONObject) crew.get(e);
            String namejob = job.getString("job");

            if (job.getString("job").equals("Director")){
                System.out.println(job);
                String resourcepersonne = "https://api.themoviedb.org/3/person/"+job.getString("id")+"?api_key="+apiKey+"&language=fr-FR";
                ResponseEntity<String> responsepersonne = template.getForEntity(resourcepersonne, String.class);
                JSONObject personneTest = new JSONObject(responsepersonne.getBody());
                Person personne = new Person();

                if (!personneTest.isNull("birthday")){
                    personne.setBirthday(LocalDate.parse(personneTest.optString("birthday")));
                }
                if (!personneTest.isNull("profile_path")){
                    String url2 = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"+personneTest.getString("profile_path");
                    InputStream image2 =  new URL(url2).openStream();
                    imageManager.savePhoto(personne, image2);
                }

                personne.setName(personneTest.getString("name"));
                personne.setIdtmdb(new BigInteger(personneTest.getString("id")));
                if (!personManager.existsByIdtmdb(personne.getIdtmdb()))
                {
                    personne = personManager.savePerson(personne);

                }
                else
                {
                    personne = personManager.findByIdTmdb(personne.getIdtmdb());
                }
                personne =  personManager.savePerson(personne);
                filmtest.setDirector(personne);
                filmtest = filmManager.saveFilm(filmtest);
                System.out.println(personne);
            }
        }
        JSONArray cast = (JSONArray) credit.get("cast");
        for (int i = 0; i < cast.length(); i++ ) {
            JSONObject role = (JSONObject) cast.get(i);
            String resourcepersonne = "https://api.themoviedb.org/3/person/"+role.getString("id")+"?api_key="+apiKey+"&language=fr-FR";
            ResponseEntity<String> responsepersonne = template.getForEntity(resourcepersonne, String.class);
            JSONObject personneTest = new JSONObject(responsepersonne.getBody());
            Person personne = new Person();

            if (!personneTest.isNull("birthday")){

                personne.setBirthday(LocalDate.parse(personneTest.optString("birthday")));
            }
            if (!personneTest.isNull("profile_path")){
                Thread.sleep(100);
                String url2 = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"+personneTest.getString("profile_path");
                InputStream image2 =  new URL(url2).openStream();
                imageManager.savePhoto(personne, image2);
                }

            personne.setName(personneTest.getString("name"));
            personne.setIdtmdb(new BigInteger(personneTest.getString("id")));
            if (!personManager.existsByIdtmdb(personne.getIdtmdb()))
            {
                personne = personManager.savePerson(personne);

            }
            else
            {
                personne = personManager.findByIdTmdb(personne.getIdtmdb());
            }
            Play play = new Play();
            play.setActor(personne);
            play.setFilm(filmtest);
            play.setName(role.getString("character"));
            play.setRank(role.getInt("order"));
            roleDao.save(play);

//            System.out.println(role.getString("name")+" joue "+ role.getString("character"));
        }
        System.out.println("FINIT");
    }
}