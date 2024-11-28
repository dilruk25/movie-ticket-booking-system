package com.dilruk.movieticketbooking.movie.repositories;

import com.dilruk.movieticketbooking.movie.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
