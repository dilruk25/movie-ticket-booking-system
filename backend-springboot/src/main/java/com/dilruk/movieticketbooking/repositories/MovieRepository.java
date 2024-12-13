package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findMovieByMovieId(String movieId);

    Optional<Movie> findMovieByTitle(String title);

    Optional<Movie> findMovieByTitleAndYear(String title, Year year);
}
