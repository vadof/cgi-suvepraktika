package com.cgi.api.services;

import com.cgi.api.entities.MovieSession;
import com.cgi.api.repostirories.MovieSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MovieSessionService {
    private final MovieSessionRepository repository;


    @Transactional
    public MovieSession saveMovieSession(MovieSession movieSession) {
        repository.save(movieSession);
        return movieSession;
    }

    @Transactional
    public List<MovieSession> getAllByDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

}
