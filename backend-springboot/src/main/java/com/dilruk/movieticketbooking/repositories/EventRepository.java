package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventByEventId(String eventId);
    List<Event> findEventsByVendorUserId(String userId);
}
