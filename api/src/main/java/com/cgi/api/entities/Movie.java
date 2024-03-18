package com.cgi.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Genre> genres;

    @Column(name = "min_age", nullable = false)
    private Integer minAge;

    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;
}
