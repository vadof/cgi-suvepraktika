package com.cgi.api.dto.tmdb;

import com.cgi.api.entities.Genre;
import lombok.Data;

import java.util.List;

@Data
public class TmdbGenresResponse {
    private List<Genre> genres;
}
