package com.dilruk.movieticketbooking.movie.services;

import com.dilruk.movieticketbooking.movie.dtos.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(String eventId);

    EventDTO updateEvent(String eventId, EventDTO eventDTO);

    void deleteEvent(String eventId);
}
