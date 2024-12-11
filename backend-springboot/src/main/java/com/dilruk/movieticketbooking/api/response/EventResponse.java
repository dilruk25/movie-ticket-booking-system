package com.dilruk.movieticketbooking.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private String eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
