package com.dilruk.movieticketbooking.cinema.repositories;

import com.dilruk.movieticketbooking.cinema.models.Multiplex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultiplexRepository extends JpaRepository<Multiplex, Long> {

    Optional<Multiplex> findMultiplexByName(String name);

    Optional<Multiplex> findMultiplexByMultiplexID(String multiplexID);

    Optional<Multiplex> findMultiplexByLocation(String location);

}
