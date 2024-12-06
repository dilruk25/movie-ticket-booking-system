package com.dilruk.movieticketbooking.ticket.repositories;

import com.dilruk.movieticketbooking.ticket.models.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}
