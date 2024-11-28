package com.dilruk.movieticketbooking.ticket.repositories;

import com.dilruk.movieticketbooking.movie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
