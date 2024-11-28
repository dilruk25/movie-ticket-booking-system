package com.dilruk.movieticketbooking.ticket.repositories;

import com.dilruk.movieticketbooking.ticket.models.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}
