package com.dilruk.movieticketbooking.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TicketDTO {

    private String ticketId;
    private double price;
    private LocalDate date;
    private LocalTime time;

}