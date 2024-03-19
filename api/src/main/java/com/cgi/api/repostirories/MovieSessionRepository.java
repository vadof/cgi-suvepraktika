package com.cgi.api.repostirories;

import com.cgi.api.entities.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {

    @Query("SELECT ms FROM MovieSession ms WHERE DATE(ms.startDate) = :date")
    List<MovieSession> findAllByDate(@Param("date") LocalDate date);

}
