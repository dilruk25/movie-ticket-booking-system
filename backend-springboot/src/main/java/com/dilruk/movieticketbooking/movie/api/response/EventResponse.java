package com.dilruk.movieticketbooking.movie.api.response;

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
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
