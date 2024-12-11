package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatId(String seatId);
}
