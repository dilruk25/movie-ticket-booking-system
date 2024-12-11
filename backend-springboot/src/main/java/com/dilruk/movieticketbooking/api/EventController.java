package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.EventMapper;
import com.dilruk.movieticketbooking.api.request.EventRequest;
import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;
    private final EventMapper eventMapper;


    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventRequest event) {
        try {
            EventDTO savedEvent = eventService.createEvent(eventMapper.fromRequestToDto(event));
            return ResponseEntity.ok(savedEvent);
        } catch (DuplicateDataException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.findAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable String id) {
        try {
            EventDTO event = eventService.findEventById(id);
            return ResponseEntity.ok(event);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable String id, @RequestBody EventRequest request) {
        try {
            EventDTO updatedEvent = eventService.updateEvent(id, eventMapper.fromRequestToDto(request));
            return ResponseEntity.ok(updatedEvent);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<EventDTO> deleteEvent(@RequestParam("eventId") String eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
