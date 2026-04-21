package com.ticketcorner.movieticketbooking.domain.movie.repository;

import com.ticketcorner.movieticketbooking.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findMovieByMovieId(String movieId);

    Optional<Movie> findMovieByTitle(String title);

    Optional<Movie> findMovieByTitleAndYear(String title, Year year);
}
