package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
