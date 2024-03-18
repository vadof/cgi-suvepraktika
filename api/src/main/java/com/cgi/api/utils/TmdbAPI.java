package com.cgi.api.utils;

import com.cgi.api.dto.tmdb.TmdbGenresResponse;
import com.cgi.api.entities.Genre;
import com.cgi.api.exceptions.AppException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TmdbAPI {

    @Value("${tmdb.api.key}")
    public String API_KEY;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public RestTemplate restTemplate;

    public List<Genre> getAllGenres() throws JsonProcessingException {
        String url = "https://api.themoviedb.org/3/genre/movie/list?language=en";

        HttpEntity<String> entity = new HttpEntity<>(getHeaders());
        ResponseEntity<TmdbGenresResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TmdbGenresResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getGenres();
        } else {
            throw new AppException("Error getting genres", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + API_KEY);
        return headers;
    }
}
