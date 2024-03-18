package com.cgi.api.config;

import com.cgi.api.entities.Genre;
import com.cgi.api.repostirories.GenreRepository;
import com.cgi.api.utils.TmdbAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer {

    private final TmdbAPI tmdbAPI;
    private final GenreRepository genreRepository;

    @Bean
    public void addGenresToDatabase() throws JsonProcessingException {
        if (genreRepository.findAll().size() == 0) {
            List<Genre> allGenres = tmdbAPI.getAllGenres();
            genreRepository.saveAll(allGenres);
        }
    }

}
