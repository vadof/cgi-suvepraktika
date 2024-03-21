package com.cgi.api.services;

import com.cgi.api.entities.Movie;
import com.cgi.api.entities.User;
import com.cgi.api.repostirories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void addWatchedMovie(User user, Movie movie) {
        user.getWatchedMovies().add(movie);
        userRepository.save(user);
    }
}
