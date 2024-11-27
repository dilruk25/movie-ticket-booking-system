package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
