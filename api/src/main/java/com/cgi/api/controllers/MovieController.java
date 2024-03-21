package com.cgi.api.controllers;

import com.cgi.api.dto.MovieDto;
import com.cgi.api.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Movies", description = "API operations with Movies")
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Get Movie recommendations based on previous viewings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list with recommended Movies",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class)))
    })
    @GetMapping("/recommendation")
    public ResponseEntity<List<MovieDto>> getMovieRecommendation(
            @RequestParam(name = "limit", required = false) Long limit) {
        log.debug("REST request to get Movie Recommendation");
        List<MovieDto> movies = movieService.getMovieRecommendation(limit);
        return ResponseEntity.ok().body(movies);
    }

}
