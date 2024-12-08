package com.dilruk.movieticketbooking.movie.services;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.EventMapper;
import com.dilruk.movieticketbooking.movie.dtos.EventDTO;
import com.dilruk.movieticketbooking.movie.models.Event;
import com.dilruk.movieticketbooking.movie.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Optional<Event> existEvent = eventRepository.findEventByTitle(eventDTO.getTitle());
        if (existEvent.isPresent()) {
            throw new DuplicateDataException("Event email already exists");
        }

        Event savedEvent = eventRepository.save(eventMapper.fromDtoToEntity(eventDTO));
        return eventMapper.fromEntityToDto(savedEvent);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventMapper::fromEntityToDto).toList();
    }

    @Override
    public EventDTO getEventById(String eventId) {
        Event existEvent = eventRepository.findEventByEventId(eventId)
                .orElseThrow(() -> new UserNotFoundException("Event not found with the id: " + eventId));

        return eventMapper.fromEntityToDto(existEvent);
    }

    @Override
    public EventDTO updateEvent(String eventId, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findEventByEventId(eventId)
                .orElseThrow(() -> new UserNotFoundException("Event not found with the id: " + eventId));

        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setStartTime(eventDTO.getStartTime());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setEndTime(eventDTO.getEndTime());
        existingEvent.setStartTime(eventDTO.getStartTime());

        Event updatedEvent = eventRepository.save(existingEvent);

        return eventMapper.fromEntityToDto(updatedEvent);
    }

    @Override
    public void deleteEvent(String eventId) {
        Event existEvent = eventRepository.findEventByEventId(eventId)
                .orElseThrow(() -> new UserNotFoundException("Event not found with the id: " + eventId));

        eventRepository.delete(existEvent);
    }
}
