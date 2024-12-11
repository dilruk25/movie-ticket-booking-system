package com.dilruk.movieticketbooking.services.event;

import com.dilruk.movieticketbooking.exceptions.EventAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.EventNotFoundException;
import com.dilruk.movieticketbooking.mappers.EventMapper;
import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.models.Event;
import com.dilruk.movieticketbooking.repositories.EventRepository;
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
        Optional<Event> existEvent = eventRepository.findEventByEventId(eventDTO.getEventId());
        if (existEvent.isPresent()) {
            throw new EventAlreadyExistsException("Event already exists");
        }

        Event savedEvent = eventRepository.save(eventMapper.fromDtoToEntity(eventDTO));
        return eventMapper.fromEntityToDto(savedEvent);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventMapper::fromEntityToDto).toList();
    }

    @Override
    public EventDTO findEventById(String eventId) {
        Event existEvent = eventRepository.findEventByEventId(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with the id: " + eventId));

        return eventMapper.fromEntityToDto(existEvent);
    }

    @Override
    public EventDTO updateEvent(String eventId, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findEventByEventId(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with the id: " + eventId));

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
                .orElseThrow(() -> new EventNotFoundException("Event not found with the id: " + eventId));

        eventRepository.delete(existEvent);
    }

    @Override
    public List<EventDTO> findEventsByVendorId(String userId) {
        return eventRepository.findEventsByVendorUserId(userId).stream().map(eventMapper::fromEntityToDto).toList();
    }
}
