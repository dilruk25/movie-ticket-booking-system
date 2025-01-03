package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findTicketByTicketId(String ticketId);

    Optional<Ticket> findTopByOrderByIdAsc();

    Optional<List<Ticket>> findTicketsByVendor_UserId(String userId);
}
