package com.dilruk.movieticketbooking.movie.repositories;

import com.dilruk.movieticketbooking.movie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findMovieByMovieId(String movieId);

    Optional<Movie> findMovieByTitle(String title);

    Optional<List<Movie>> findMoviesByGenre(String genre);
}
