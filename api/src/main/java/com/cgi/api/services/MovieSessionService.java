package com.cgi.api.services;

import com.cgi.api.config.Constants;
import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.dto.SeatsInfo;
import com.cgi.api.entities.MovieSession;
import com.cgi.api.exceptions.AppException;
import com.cgi.api.mappers.MovieSessionMapper;
import com.cgi.api.repostirories.MovieSessionRepository;
import com.cgi.api.utils.SeatRecommendation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MovieSessionService {
    private final MovieSessionRepository repository;
    private final MovieSessionMapper mapper;

    @Transactional
    public MovieSession saveMovieSession(MovieSession movieSession) {
        repository.save(movieSession);
        return movieSession;
    }

    public List<MovieSession> getAllMovieSessionsByDate(LocalDate date) {
        LocalDateTime now = LocalDateTime.now();
        return repository.findAllByDate(date).stream()
                .filter((movieSession -> movieSession.getEndDate().isAfter(now)))
                .toList();
    }

    public List<MovieSessionDto> getAllMovieSessionsDtoByDate(LocalDate date) {
        return mapper.toDtos(getAllMovieSessionsByDate(date));
    }

    public List<LocalDate> getAllUpcomingMovieScheduleDates() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = LocalDate.now();
        while (repository.findAllByDate(date).size() > 0) {
            dates.add(date);
            date = date.plusDays(1);
        }

        return dates;
    }

    public SeatsInfo getSeatsInfo(Long movieSessionId, Integer peopleAmount) {
        MovieSession movieSession = getMovieSession(movieSessionId);

        if (movieSession.getEndDate().isBefore(LocalDateTime.now())) {
            if (peopleAmount > movieSession.getSeatsAvailable()) {
                throw new AppException("No available seats for " + peopleAmount + " persons", HttpStatus.BAD_REQUEST);
            }

            int[][] seats = new int[Constants.CINEMA_ROWS][Constants.CINEMA_SEATS_IN_ROW];
            SeatRecommendation.markReservedSeats(seats, movieSession.getTickets());
            SeatRecommendation.markRecommendedSeats(seats, peopleAmount,
                    Constants.CINEMA_ROWS, Constants.CINEMA_SEATS_IN_ROW, 3);

            return new SeatsInfo(Constants.CINEMA_ROWS, Constants.CINEMA_SEATS_IN_ROW, seats);
        } else {
            throw new AppException("The session has already ended", HttpStatus.BAD_REQUEST);
        }
    }

    private MovieSession getMovieSession(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new AppException("MovieSession#" + id + " not found", HttpStatus.NOT_FOUND));
    }
}
