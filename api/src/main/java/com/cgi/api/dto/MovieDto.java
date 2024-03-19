package com.cgi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private Boolean adult;
    private String title;
    private List<GenreDto> genres;

    @JsonProperty("overview")
    private String description;

    @JsonProperty("original_language")
    private String language;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private BigDecimal rating;

    @JsonProperty("poster_path")
    private String imageSrc;

    @JsonProperty("runtime")
    private Integer durationInMinutes;
}
