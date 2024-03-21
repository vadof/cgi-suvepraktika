package com.cgi.api.utils;

import com.cgi.api.dto.MovieDto;
import com.cgi.api.dto.tmdb.TmdbGenresResponse;
import com.cgi.api.entities.Genre;
import com.cgi.api.entities.Movie;
import com.cgi.api.exceptions.AppException;
import com.cgi.api.services.MovieService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TmdbAPI {

    @Value("${tmdb.api.key}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieService movieService;

    public List<Genre> getAllGenres() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?language=en";

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        ResponseEntity<TmdbGenresResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbGenresResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getGenres();
        } else {
            throw new AppException("Error getting genres", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Movie> getNowPlayingMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?region=EE&page=1";

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        List<Movie> movies = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Long> movieIds = extractTmdbMovieIds(response.getBody());

            for (Long id : movieIds) {
                Optional<Movie> movie = movieService.findMovieById(id);
                if (movie.isEmpty()) {
                    try {
                        MovieDto movieDto = getMovieById(id);
                        movies.add(movieService.saveMovie(movieDto));
                    } catch (AppException ignored) {}
                } else {
                    movies.add(movie.get());
                }
            }
        } else {
            throw new AppException("Error getting genres", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return movies;
    }

    public MovieDto getMovieById(Long id) {
        String url = "https://api.themoviedb.org/3/movie/" + id;

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        ResponseEntity<MovieDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, MovieDto.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            MovieDto movie = response.getBody();
            if (movie.getImageSrc() != null) {
                movie.setImageSrc("https://image.tmdb.org/t/p/original" + movie.getImageSrc());
            }
            return movie;
        } else {
            throw new AppException("Error getting Movie#" + id + " from TMDB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Long> extractTmdbMovieIds(String jsonResponse) {
        ObjectMapper mapper = new ObjectMapper();
        List<Long> ids = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode resultsNode = root.get("results");

            if (resultsNode != null && resultsNode.isArray()) {
                for (JsonNode node : resultsNode) {
                    JsonNode idNode = node.get("id");
                    if (idNode != null && idNode.isNumber()) {
                        ids.add(idNode.asLong());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + API_KEY);
        return headers;
    }
}
