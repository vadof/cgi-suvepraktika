package com.cgi.api.controllers;

import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.services.MovieSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie-sessions")
@AllArgsConstructor
@Slf4j
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    @Operation(summary = "Get Movie Session list at specified date (yyyy-MM-dd)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return Movie Session list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieSessionDto.class)))
    })
    @GetMapping("/{date}")
    public ResponseEntity<List<MovieSessionDto>> getMovieSession(
            @PathVariable(value = "date") LocalDate date) {
        log.debug("REST request to get movie session");
        List<MovieSessionDto> dtoList = movieSessionService.getAllDtoByDate(date);
        return ResponseEntity.ok().body(dtoList);
    }

    @Operation(summary = "Get all upcoming dates that already have movie schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return LocalDate list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocalDate.class)))
    })
    @GetMapping("/dates")
    public ResponseEntity<List<LocalDate>> getMovieSession() {
        log.debug("REST request to get upcoming movie sessions dates");
        List<LocalDate> dates = movieSessionService.getAllUpcomingMovieScheduleDates();
        return ResponseEntity.ok().body(dates);
    }
}
