package com.cgi.api.services;

import com.cgi.api.dto.MovieDto;
import com.cgi.api.entities.Genre;
import com.cgi.api.entities.Movie;
import com.cgi.api.entities.User;
import com.cgi.api.exceptions.AppException;
import com.cgi.api.mappers.MovieMapper;
import com.cgi.api.repostirories.MovieRepository;
import com.cgi.api.repostirories.MovieSessionRepository;
import com.cgi.api.services.common.GenericService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService extends GenericService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieSessionRepository movieSessionRepository;

    public Optional<Movie> findMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    public Movie saveMovie(MovieDto movieDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieDto.getId());
        if (optionalMovie.isEmpty()) {
            Movie movie = movieMapper.toEntity(movieDto);
            movieRepository.save(movie);
            log.info("New Movie saved to database {}", movie);
            return movie;
        } else {
            return optionalMovie.get();
        }
    }

    public List<MovieDto> getMovieRecommendation(@Nullable Long limit) {
        User user = getCurrentUserAsEntity();
        List<Movie> watchedMovies = user.getWatchedMovies();

        List<Movie> moviesInSchedule = getAllMoviesInCinemaSchedule();
        Stream<Movie> sortedPreference;

        if (watchedMovies.size() > 0) {
            moviesInSchedule = removeAlreadyWatchedMovies(watchedMovies, moviesInSchedule);
            Map<Movie, Integer> moviePreference = calculateMoviePreference(moviesInSchedule,
                    getGenresRate(watchedMovies));
            sortedPreference = moviesInSchedule.stream()
                    .sorted(Comparator.comparingInt(moviePreference::get).reversed());
        } else {
            sortedPreference = moviesInSchedule.stream()
                    .sorted(Comparator.comparingDouble((Movie m) -> m.getRating().doubleValue()).reversed());
        }

        if (limit != null) {
            return movieMapper.toDtos(sortedPreference.limit(limit).toList());
        }

        return movieMapper.toDtos(sortedPreference.toList());
    }

    private Map<Long, Integer> getGenresRate(List<Movie> movies) {
        Map<Long, Integer> map = new HashMap<>();
        for (Movie movie : movies) {
            for (Genre genre : movie.getGenres()) {
                map.put(genre.getId(), map.getOrDefault(genre.getId(), 0) + 1);
            }
        }

        return map;
    }

    private Map<Movie, Integer> calculateMoviePreference(List<Movie> movies, Map<Long, Integer> genresRate) {
        Map<Movie, Integer> map = new HashMap<>();
        for (Movie movie : movies) {

            int genresValue = 0;
            for (Genre genre : movie.getGenres()) {
                genresValue += genresRate.getOrDefault(genre.getId(), 0);
            }

            map.put(movie, genresValue);
        }

        return map;
    }

    private List<Movie> getAllMoviesInCinemaSchedule() {
        return movieSessionRepository.findAllUniqueMovies(LocalDateTime.now());
    }

    private List<Movie> removeAlreadyWatchedMovies(List<Movie> watched, List<Movie> movies) {
        Set<Long> watchedIds = watched.stream()
                .map(Movie::getId).collect(Collectors.toSet());

        return movies.stream()
                .filter(movie -> !watchedIds.contains(movie.getId()))
                .toList();
    }

    public MovieDto getMovieById(Long id) {
        return movieMapper.toDto(getMovie(id));
    }

    private Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(
                () -> new AppException("Movie#" + id + " not found", HttpStatus.NOT_FOUND));
    }
}
