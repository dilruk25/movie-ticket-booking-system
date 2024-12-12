package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.multiplex.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
