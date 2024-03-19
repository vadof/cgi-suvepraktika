package com.cgi.api.services;

import com.cgi.api.config.Constants;
import com.cgi.api.entities.Movie;
import com.cgi.api.entities.MovieSession;
import com.cgi.api.utils.TmdbAPI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CinemaScheduleService {

    private final TmdbAPI tmdbAPI;
    private final MovieSessionService movieSessionService;


    // TODO test function
    @Scheduled(cron = "0 0 0 * * *")
    public void updateMovieSessionSchedule() {
        createMovieSessionSchedule();
    }

    public void createMovieSessionSchedule() {
        LocalDate date = LocalDate.now();
        List<Movie> movies = tmdbAPI.getNowPlayingMovies();

        for (int i = 0; i <= 7; i++) {
            LocalDate nextDay = date.plusDays(i);
            if (movieSessionService.getAllByDate(nextDay).size() == 0) {
                createScheduleForDay(movies, nextDay);
            }
        }
    }

    private void createScheduleForDay(List<Movie> movies, LocalDate date) {
        Collections.shuffle(movies);

        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.of(Constants.CINEMA_OPEN_TIME_HOURS, Constants.CINEMA_OPEN_TIME_MINUTES));
        LocalDateTime closeDate = LocalDateTime.of(date, LocalTime.of(Constants.CINEMA_CLOSE_TIME_HOURS, Constants.CINEMA_CLOSE_TIME_MINUTES));

        for (Movie movie : movies) {
            LocalDateTime endDate = startDate.plusMinutes(movie.getDurationInMinutes());

            if (endDate.isBefore(closeDate) || endDate.isEqual(closeDate)) {
                MovieSession session = new MovieSession();
                session.setMovie(movie);
                session.setStartDate(startDate);
                session.setEndDate(endDate);

                movieSessionService.saveMovieSession(session);

                startDate = roundToNearestQuarterHour(endDate);
            }
        }

        log.info("The schedule of cinema shows for {} has been compiled", date);
    }

    private LocalDateTime roundToNearestQuarterHour(LocalDateTime date) {
        int minutes = date.getMinute();
        if (minutes % 15 < 8) {
            return date.truncatedTo(ChronoUnit.HOURS).plusMinutes(minutes - (minutes % 15));
        } else {
            return date.truncatedTo(ChronoUnit.HOURS).plusMinutes(minutes + (15 - (minutes % 15)));
        }
    }

}
