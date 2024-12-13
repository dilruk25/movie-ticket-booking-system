package com.dilruk.movieticketbooking.dtos;

import com.dilruk.movieticketbooking.enums.IdPrefix;
import com.dilruk.movieticketbooking.utils.IdGenerator;
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

    public TicketDTO() {
        this.ticketId = IdGenerator.generateId(IdPrefix.TICKET_PREFIX.getPrefix());
    }
}