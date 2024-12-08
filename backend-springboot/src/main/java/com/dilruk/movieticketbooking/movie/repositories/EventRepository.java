package com.dilruk.movieticketbooking.movie.repositories;

import com.dilruk.movieticketbooking.movie.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventByEventId(String eventId);

    Optional<Event> findEventByTitle(String title);

}
