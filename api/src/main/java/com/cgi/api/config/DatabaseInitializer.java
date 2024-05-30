package com.cgi.api.config;

import com.cgi.api.entities.Genre;
import com.cgi.api.repostirories.GenreRepository;
import com.cgi.api.services.CinemaScheduleService;
import com.cgi.api.utils.TmdbAPI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DatabaseInitializer {

    private final TmdbAPI tmdbAPI;
    private final GenreRepository genreRepository;
    private final CinemaScheduleService cinemaScheduleService;

    @Bean
    public void addGenresToDatabase() {
        if (genreRepository.findAll().isEmpty()) {
            List<Genre> allGenres = tmdbAPI.getAllGenres();
            genreRepository.saveAll(allGenres);
            log.info("Filled Genre table");
        }
    }

    @Bean
    public void setCinemaSchedule() {
        cinemaScheduleService.createMovieSessionSchedule();
        log.info("Cinema schedule has been drawn up");
    }

}
