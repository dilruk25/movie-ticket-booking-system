package com.dilruk.movieticketbooking.dtos;

import com.dilruk.movieticketbooking.enums.IdPrefix;
import com.dilruk.movieticketbooking.utils.IdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class EventDTO {

    private String eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public EventDTO() {
        this.eventId = IdGenerator.generateId(IdPrefix.EVENT_PREFIX.getPrefix());
    }
}
