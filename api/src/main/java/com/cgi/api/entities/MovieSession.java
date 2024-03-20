package com.cgi.api.entities;

import com.cgi.api.config.Constants;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieSession {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "seats_available", nullable = false)
    private Integer seatsAvailable = Constants.CINEMA_ROWS * Constants.CINEMA_SEATS_IN_ROW;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "movieSession")
    private List<Ticket> tickets = new ArrayList<>();
}
