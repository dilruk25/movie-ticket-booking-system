package com.dilruk.movieticketbooking.services.event;

import com.dilruk.movieticketbooking.dtos.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);

    List<EventDTO> findAllEvents();

    EventDTO findEventById(String eventId);

    EventDTO updateEvent(String eventId, EventDTO eventDTO);

    void deleteEvent(String eventId);

    List<EventDTO> findEventsByVendorId(String userId);
}
