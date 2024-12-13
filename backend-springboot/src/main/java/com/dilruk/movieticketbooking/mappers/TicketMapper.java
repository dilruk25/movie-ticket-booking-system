package com.dilruk.movieticketbooking.mappers;

import com.dilruk.movieticketbooking.dtos.TicketDTO;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket fromDtoToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketDTO.getTicketId());
        ticket.setDate(ticketDTO.getDate());
        ticket.setStartTime(ticketDTO.getTime());
        ticket.setPrice(ticketDTO.getPrice());

        return ticket;
    }

    public TicketDTO fromEntityToDto(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(ticket.getTicketId());
        ticketDTO.setDate(ticket.getDate());
        ticketDTO.setTime(ticket.getStartTime());
        ticketDTO.setPrice(ticket.getPrice());

        return ticketDTO;
    }
}