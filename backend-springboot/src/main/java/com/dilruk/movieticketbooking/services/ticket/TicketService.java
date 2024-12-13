package com.dilruk.movieticketbooking.services.ticket;

import com.dilruk.movieticketbooking.api.request.AddTicketsRequest;
import com.dilruk.movieticketbooking.dtos.TicketDTO;

import java.util.List;

public interface TicketService {

    TicketDTO createTicket(TicketDTO ticketDTO);

    List<TicketDTO> findAllTickets();

    TicketDTO findTicketById(String ticketId);

    void deleteTicket(String ticketId);

    List<TicketDTO> findTicketsByVendorId(String userId);

    List<TicketDTO> addTickets(AddTicketsRequest request, String vendorId);
}
