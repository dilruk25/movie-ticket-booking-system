package com.dilruk.movieticketbooking.cinema.repositories;

import com.dilruk.movieticketbooking.cinema.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
