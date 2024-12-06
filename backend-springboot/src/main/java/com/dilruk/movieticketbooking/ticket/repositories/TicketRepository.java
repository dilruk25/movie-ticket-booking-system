package com.dilruk.movieticketbooking.ticket.repositories;

import com.dilruk.movieticketbooking.ticket.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
