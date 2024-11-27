package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
