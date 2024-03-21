package com.cgi.api.repostirories;

import com.cgi.api.entities.Movie;
import com.cgi.api.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    @Query("SELECT ms FROM MovieSession ms WHERE DATE(ms.startDate) = :date")
    List<MovieSession> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT ms.movie FROM MovieSession ms WHERE ms.endDate > :date")
    List<Movie> findAllUniqueMovies(@Param("date") LocalDateTime afterDate);
}
