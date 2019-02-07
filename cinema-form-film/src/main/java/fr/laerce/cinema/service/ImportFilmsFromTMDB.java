package fr.laerce.cinema.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.io.ByteStreams;
import fr.laerce.cinema.dao.TmdbFilmDao;
import fr.laerce.cinema.model.TmdbFilm;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPInputStream;

@Component
public class ImportFilmsFromTMDB {

    /**
     * ETL for movies : import movies from TMDB (The Movie DataBase)
     * ImportMoviesViaOnlineTmdbFile() processes the movies list file directly from TMDB website
     * ImportMoviesViaLocalTempTmdbFile()load the movies list file from TMDB website to tmpFilePath for local process...
     * ... and avoid timeout because of limit request for TMDB API
     * Please choose one of the two methods to call in controller (Admin Controller)
     *
     * */

    @Value("${cinema.tmp.path}")
    private String tmpFilePath;

    @Autowired
    private TmdbFilmDao tmdbFilmDao;

    public ImportFilmsFromTMDB(TmdbFilmDao tmdbFilmDao) {
        this.tmdbFilmDao = tmdbFilmDao;
    }

    public void ImportMoviesViaOnlineTmdbFile() {

        // we recover the date of the day before to avoid loading a file still unpublished by TMDV
        LocalDate date = LocalDate.now().minusDays(1);
        // date format to add zeros for months and days less than 10
        String day = String.format("%02d", date.getDayOfMonth());
        String month = String.format("%02d", date.getMonthValue());
        String year = String.valueOf(date.getYear());


        // url construction
        String fileName = "movie_ids_"+month+"_"+day+"_"+year+".json.gz";
        String url = "http://files.tmdb.org/p/exports/"+fileName;

        try {
            // file stream
            InputStream httpIS = new URL(url).openStream();
            // unzip file
            InputStream gzipIS = new GZIPInputStream(httpIS);
            // buffering the file : read block by block to gain performance
            InputStream bufferedIS = new BufferedInputStream(gzipIS);

            System.out.println(bufferedIS);

            // parsing the file with json.org library
            BufferedReader br = new BufferedReader(new InputStreamReader(bufferedIS, StandardCharsets.UTF_8));
            String line;
            while((line = br.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                String title = json.get("original_title").toString();
                long tmdbId = Long.valueOf(json.get("id").toString());
                boolean adult = Boolean.valueOf(json.get("adult").toString());
                TmdbFilm film = new TmdbFilm(title, tmdbId);
                if(!adult && tmdbFilmDao.findByTmdbid(tmdbId) == null) {
                    tmdbFilmDao.save(film);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ImportMoviesViaLocalTempTmdbFile() {
        // we recover the date of the day before to avoid loading a file still unpublished by TMDV
        LocalDate date = LocalDate.now().minusDays(1);
        // date format to add zeros for months and days less than 10
        String day = String.format("%02d", date.getDayOfMonth());
        String month = String.format("%02d", date.getMonthValue());
        String year = String.valueOf(date.getYear());


        // url construction
        String fileName = "movie_ids_"+month+"_"+day+"_"+year+".json.gz";
        String url = "http://files.tmdb.org/p/exports/"+fileName;

        String tmpFile = tmpFilePath+""+fileName;

        try {
            // file stream
            InputStream is = new URL(url).openStream();
            // write file stream datas in a temp local file (tmpFile)
            FileOutputStream fos = new FileOutputStream(tmpFile);
            // do it with Guava (Google API for Java)
            ByteStreams.copy(is, fos);
            is.close();
            fos.flush();
            fos.close();
            BufferedInputStream bis = new BufferedInputStream(new GZIPInputStream(new FileInputStream(tmpFile)));

            // parsing the file with json.org library
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));
            String line;
            while((line = br.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                String title = json.get("original_title").toString();
                long tmdbId = Long.valueOf(json.get("id").toString());
                boolean adult = Boolean.valueOf(json.get("adult").toString());
                TmdbFilm film = new TmdbFilm(title, tmdbId);
                if(!adult && tmdbFilmDao.findByTmdbid(tmdbId) == null) {
                    tmdbFilmDao.save(film);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
