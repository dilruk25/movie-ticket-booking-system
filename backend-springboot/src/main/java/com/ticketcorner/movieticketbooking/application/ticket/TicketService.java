package com.ticketcorner.movieticketbooking.application.ticket;

import com.ticketcorner.movieticketbooking.api.v1.ro.request.AddTicketsRequest;
import com.ticketcorner.movieticketbooking.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    TicketDTO createTicket(TicketDTO ticketDTO);

    List<TicketDTO> addTickets(AddTicketsRequest request);

    List<TicketDTO> findAllTickets();

    TicketDTO findTicketById(String ticketId);

    void deleteTicket(String ticketId);

    List<TicketDTO> findTicketsByVendorId(String userId);

}
