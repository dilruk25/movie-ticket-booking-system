package com.dilruk.movieticketbooking.mappers;

import com.dilruk.movieticketbooking.api.request.EventRequest;
import com.dilruk.movieticketbooking.api.response.EventResponse;
import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.models.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDTO fromRequestToDto(EventRequest request) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(request.getDate());
        eventDTO.setStartTime(request.getStartTime());
        eventDTO.setEndTime(request.getEndTime());

        return eventDTO;
    }

    public Event fromDtoToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setEventId(eventDTO.getEventId());
        event.setDate(eventDTO.getDate());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());

        return event;
    }

    public EventDTO fromEntityToDto(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setDate(event.getDate());
        eventDTO.setStartTime(event.getStartTime());
        eventDTO.setEndTime(event.getEndTime());

        return eventDTO;
    }

    public EventResponse fromDtoToResponse(EventDTO eventDTO) {
        EventResponse response = new EventResponse();
        response.setEventId(eventDTO.getEventId());
        response.setDate(eventDTO.getDate());
        response.setStartTime(eventDTO.getStartTime());
        response.setEndTime(eventDTO.getEndTime());

        return response;
    }
}