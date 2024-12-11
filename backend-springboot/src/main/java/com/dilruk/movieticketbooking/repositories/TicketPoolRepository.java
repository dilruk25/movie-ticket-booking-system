package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}
