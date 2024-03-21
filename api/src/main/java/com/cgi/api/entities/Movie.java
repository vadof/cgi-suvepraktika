package com.cgi.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres;

    @Column(name = "adult", nullable = false)
    private Boolean adult;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "image_src")
    private String imageSrc;

    @Column(name = "rating", columnDefinition = "DECIMAL(4,2)")
    private BigDecimal rating;
}
