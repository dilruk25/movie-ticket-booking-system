package com.dilruk.movieticketbooking.cinema.repositories;

import com.dilruk.movieticketbooking.cinema.models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
