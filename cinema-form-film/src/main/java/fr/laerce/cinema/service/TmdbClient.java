package fr.laerce.cinema.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


@Component
public class TmdbClient {

    @Value("${tmdb.api.key}")
    private String apiKey;

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

    public void getMovieByTmdbId(long id) throws Exception {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;
        long reset;

        String resourceUrl = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+apiKey+"&language=fr-FR";
        response = template.getForEntity(resourceUrl, String.class);
        System.out.println(response.getBody());
        JSONObject film = new JSONObject(response.getBody());
        JSONArray genres = (JSONArray) film.get("genres");
        System.out.println("Titre : "+film.getString("title"));

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
        JSONArray cast = (JSONArray) credit.get("cast");
        for (int i = 0; i < cast.length(); i++ ) {
            JSONObject role = (JSONObject) cast.get(i);
            System.out.println(role.getString("name")+" joue "+ role.getString("character"));
        }
        System.out.println("--------\nRequetes restantes : "+stripBraces(response.getHeaders().get("x-ratelimit-remaining").toString()));
        reset = reset = secondsBeforeReset(response.getHeaders().get("x-ratelimit-reset").toString());
        System.out.println("Temps restant avant reset : "+reset+"\n\n");
    }
}