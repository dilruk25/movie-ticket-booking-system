package com.ticketcorner.movieticketbooking.common.mappers;

import com.ticketcorner.movieticketbooking.dto.TicketDTO;
import com.ticketcorner.movieticketbooking.common.enums.IdPrefixEnum;
import com.ticketcorner.movieticketbooking.domain.ticket.entity.Ticket;
import com.ticketcorner.movieticketbooking.common.utils.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket fromDtoToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(IdGenerator.generateId(IdPrefixEnum.TICKET_PREFIX.getPrefix()));
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