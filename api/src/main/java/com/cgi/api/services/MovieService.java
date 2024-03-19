package com.cgi.api.services;

import com.cgi.api.dto.MovieDto;
import com.cgi.api.entities.Movie;
import com.cgi.api.mappers.MovieMapper;
import com.cgi.api.repostirories.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public Optional<Movie> findMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    public Movie saveMovie(MovieDto movieDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieDto.getId());
        if (optionalMovie.isEmpty()) {
            Movie movie = movieMapper.toEntity(movieDto);
            if (movie.getImageSrc() != null) {
                movie.setImageSrc("https://image.tmdb.org/t/p/original" + movie.getImageSrc());
            }
            movieRepository.save(movie);
            log.info("New Movie saved to database {}", movie);
            return movie;
        } else {
            return optionalMovie.get();
        }
    }
}
