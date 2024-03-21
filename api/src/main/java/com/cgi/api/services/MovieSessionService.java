package com.cgi.api.services;

import com.cgi.api.config.Constants;
import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.dto.SeatsInfo;
import com.cgi.api.dto.TicketDto;
import com.cgi.api.entities.MovieSession;
import com.cgi.api.entities.Ticket;
import com.cgi.api.entities.User;
import com.cgi.api.exceptions.AppException;
import com.cgi.api.mappers.MovieSessionMapper;
import com.cgi.api.mappers.TicketMapper;
import com.cgi.api.repostirories.MovieSessionRepository;
import com.cgi.api.services.common.GenericService;
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
public class MovieSessionService extends GenericService {
    private final MovieSessionRepository repository;
    private final MovieSessionMapper movieSessionMapper;
    private final TicketMapper ticketMapper;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final UserService userService;

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
        return movieSessionMapper.toDtos(getAllMovieSessionsByDate(date));
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

        if (movieSessionEnded(movieSession)) {
            throw new AppException("The session has already ended", HttpStatus.BAD_REQUEST);
        }

        if (peopleAmount > movieSession.getSeatsAvailable()) {
            throw new AppException("No available seats for " + peopleAmount + " persons", HttpStatus.BAD_REQUEST);
        }

        int[][] seats = new int[Constants.CINEMA_ROWS][Constants.CINEMA_SEATS_IN_ROW];
        seatService.markReservedSeats(seats, movieSession.getTickets());
        seatService.markRecommendedSeats(seats, peopleAmount,
                Constants.CINEMA_ROWS, Constants.CINEMA_SEATS_IN_ROW, 3);

        return new SeatsInfo(Constants.CINEMA_ROWS, Constants.CINEMA_SEATS_IN_ROW, seats);
    }

    @Transactional
    public List<TicketDto> buyTickets(List<TicketDto> ticketsDto, Long movieSessionId) {
        MovieSession movieSession = getMovieSession(movieSessionId);

        if (movieSessionEnded(movieSession)) {
            throw new AppException("The session has already ended", HttpStatus.BAD_REQUEST);
        }

        List<Ticket> tickets = ticketMapper.toEntities(ticketsDto);
        User user = getCurrentUserAsEntity();

        List<TicketDto> savedTickets = new ArrayList<>(ticketsDto.size());
        validateSeats(tickets, movieSession);

        for (Ticket ticket : tickets) {
            ticket.setMovieSession(movieSession);
            ticket.setUser(user);
            savedTickets.add(ticketService.saveTicket(ticket));
        }

        userService.addWatchedMovie(user, movieSession.getMovie());
        movieSession.setSeatsAvailable(movieSession.getSeatsAvailable() - tickets.size());
        repository.save(movieSession);

        return savedTickets;
    }

    private void validateSeats(List<Ticket> tickets, MovieSession movieSession) {
        if (movieSession.getSeatsAvailable() < tickets.size()) {
            throw new AppException("Not enough available seats", HttpStatus.BAD_REQUEST);
        }

        int[][] seats = new int[Constants.CINEMA_ROWS][Constants.CINEMA_SEATS_IN_ROW];
        seatService.markReservedSeats(seats, movieSession.getTickets());

        seatService.validateTickets(seats, Constants.CINEMA_ROWS,
                Constants.CINEMA_SEATS_IN_ROW, tickets);
    }

    private MovieSession getMovieSession(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new AppException("MovieSession#" + id + " not found", HttpStatus.NOT_FOUND));
    }

    private boolean movieSessionEnded(MovieSession movieSession) {
        return LocalDateTime.now().isAfter(movieSession.getEndDate());
    }

    public List<MovieSessionDto> getAllMovieSessionsByMovie(Long movieId) {
        return movieSessionMapper.toDtos(repository.findAllByMovieAndDate(movieId, LocalDateTime.now()));
    }
}
