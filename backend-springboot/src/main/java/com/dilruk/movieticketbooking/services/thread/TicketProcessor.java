package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.models.pools.TicketPool;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TicketProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TicketProcessor.class);
    private final TicketPool ticketPool;

    public TicketProcessor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void addTickets() {
        ticketPool.addTicket(new Ticket());
    }

    public Ticket buyTicket() {
       return ticketPool.buyTicket();
    }

    /**
     * Retrieves the current count of tickets in the pool.
     *
     * @return The number of tickets in the pool.
     */
    public int getTicketCount() {
        int count = ticketPool.getTicketCount();
        logger.info("Current ticket count in pool: {}", count);
        return count;
    }

    /**
     * Generates a unique ticket ID. This can be replaced with a more robust ID generator.
     *
     * @return A unique ticket ID.
     */
    private String generateTicketId() {
        return "TICKET-" + System.nanoTime();
    }
}