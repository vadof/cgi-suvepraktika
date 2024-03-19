package com.cgi.api.services;

import com.cgi.api.dto.MovieSessionDto;
import com.cgi.api.entities.MovieSession;
import com.cgi.api.mappers.MovieSessionMapper;
import com.cgi.api.repostirories.MovieSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public List<MovieSession> getAllByDate(LocalDate date) {
        return repository.findAllByDate(date);
    }

    public List<MovieSessionDto> getAllDtoByDate(LocalDate date) {
        return mapper.toDtos(getAllByDate(date));
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
}
