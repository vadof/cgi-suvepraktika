package com.cgi.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieSessionDto {
    private Long id;
    private MovieDto movie;
    private Integer seatsAvailable;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
