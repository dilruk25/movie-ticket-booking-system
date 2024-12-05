package com.dilruk.movieticketbooking.cinema.repositories;

import com.dilruk.movieticketbooking.cinema.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatId(String seatId);
}
