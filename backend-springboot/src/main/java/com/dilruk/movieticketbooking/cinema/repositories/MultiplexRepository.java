package com.dilruk.movieticketbooking.cinema.repositories;

import com.dilruk.movieticketbooking.cinema.models.Multiplex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiplexRepository extends JpaRepository<Multiplex, Long> {
}
