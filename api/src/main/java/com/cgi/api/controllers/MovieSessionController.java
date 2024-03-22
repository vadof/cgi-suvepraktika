package com.cgi.api.controllers;

import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.dto.SeatsInfo;
import com.cgi.api.dto.TicketDto;
import com.cgi.api.services.MovieSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Movies Session", description = "API operations with Movie Sessions")
@RestController
@RequestMapping("/api/v1/movie-sessions")
@AllArgsConstructor
@Slf4j
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    @Operation(summary = "Get upcoming Movie Session list at specified date (yyyy-MM-dd)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return Movie Session list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class)))
    })
    @GetMapping("/dates/{date}")
    public ResponseEntity<List<MovieSessionDto>> getMovieSession(
            @PathVariable(value = "date") LocalDate date) {
        log.debug("REST request to get movie sessions at {}", date);
        List<MovieSessionDto> dtoList = movieSessionService.getAllMovieSessionsDtoByDate(date);
        return ResponseEntity.ok().body(dtoList);
    }

    @Operation(summary = "Get all upcoming dates that already have movie schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return LocalDate list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocalDate.class)))
    })
    @GetMapping("/dates")
    public ResponseEntity<List<LocalDate>> getMovieSession() {
        log.debug("REST request to get upcoming movie sessions dates");
        List<LocalDate> dates = movieSessionService.getAllUpcomingMovieScheduleDates();
        return ResponseEntity.ok().body(dates);
    }

    @Operation(summary = "Get information about seats in the cinema hall")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return 2D array (0 - seat free, 1 - seat reserved, 2 - seat recommended",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeatsInfo.class))),
            @ApiResponse(responseCode = "400", description = "The session has already ended | No available seats",
                    content = @Content(mediaType = "*/*"))
    })
    @GetMapping("/seats")
    public ResponseEntity<SeatsInfo> getSeatsInfo(@RequestParam(name = "movieSessionId") Long movieSessionId,
                                                  @RequestParam(name = "people") Integer people) {
        log.debug("REST request to get SeatsInfo for MovieSession#{} for {} people", movieSessionId, people);
        SeatsInfo seatsInfo = movieSessionService.getSeatsInfo(movieSessionId, people);
        return ResponseEntity.ok().body(seatsInfo);
    }

    @Operation(summary = "Buy Tickets to a Movie Session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list with purchased Tickets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect Ticket data",
                    content = @Content(mediaType = "*/*"))
    })
    @PostMapping("/{movieSessionId}/buy-tickets")
    public ResponseEntity<List<TicketDto>> buyTickets(@PathVariable(name = "movieSessionId") Long movieSessionId,
                                                  @RequestBody List<TicketDto> tickets) {
        log.debug("REST request to buy {} Tickets for MovieSession#{}", tickets.size(), movieSessionId);
        List<TicketDto> ticketList = movieSessionService.buyTickets(tickets, movieSessionId);
        return ResponseEntity.ok().body(ticketList);
    }

    @Operation(summary = "Get all Movie Sessions by Movie id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list with Movie Sessions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))),
    })
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<MovieSessionDto>> getAllMovieSessionsByMovie(@PathVariable(name = "movieId") Long movieId) {
        log.debug("REST request to get all Movie Sessions with Movie#{}", movieId);
        List<MovieSessionDto> movies = movieSessionService.getAllMovieSessionsByMovie(movieId);
        return ResponseEntity.ok().body(movies);
    }

    @Operation(summary = "Get all Movie Sessions by Movie id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return list with Movie Sessions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieSessionDto.class))),
            @ApiResponse(responseCode = "404", description = "Movie Session not found",
                    content = @Content(mediaType = "*/*"))
    })
    @GetMapping("/{movieSessionId}")
    public ResponseEntity<MovieSessionDto> getMovieSessionById(@PathVariable(name = "movieSessionId") Long movieSessionId) {
        log.debug("REST request to get all MovieSession#{}", movieSessionId);
        MovieSessionDto movieSession = movieSessionService.getMovieSessionById(movieSessionId);
        return ResponseEntity.ok().body(movieSession);
    }
}
