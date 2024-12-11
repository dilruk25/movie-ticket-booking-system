package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.Multiplex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultiplexRepository extends JpaRepository<Multiplex, Long> {

    Optional<Multiplex> findMultiplexByName(String name);

    Optional<Multiplex> findMultiplexByMultiplexID(String multiplexID);

    Optional<Multiplex> findMultiplexByLocation(String location);

}
