package com.dilruk.movieticketbooking.api.request;

import jakarta.validation.constraints.NotEmpty;
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
public class EventRequest {

    @NotEmpty
    private LocalDate date;
    @NotEmpty
    private LocalTime startTime;
    @NotEmpty
    private LocalTime endTime;

}
