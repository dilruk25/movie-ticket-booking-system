package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.models.pools.TicketPool;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A service responsible for processing ticket transactions.
 * It interacts with the TicketPool to add and buy tickets, and provides
 * methods to get the current ticket count in the pool.
 */
@Component
@Getter
@Setter
public class TicketProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TicketPool ticketPool;

    /**
     * Constructor to initialize the TicketProcessor with the given TicketPool.
     *
     * @param ticketPool The TicketPool instance that this processor will interact with.
     */
    public TicketProcessor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    /**
     * Adds a new ticket to the pool.
     * The ticket is created using the Ticket constructor and added to the pool.
     *<p>
     * This method will delegate to the TicketPool to add a new ticket if there's capacity.
     */
    public boolean addTickets() {
        Ticket newTicket = new Ticket(); // Create a new ticket
        if (ticketPool.addTicket(newTicket)) {
            logger.info("Ticket added successfully: {}", newTicket);
            return true;
        } else {
            logger.warn("Failed to add ticket, pool is full.");
            return false;
        }
    }

    /**
     * Attempts to buy a ticket from the pool.
     * It will delegate the task to the TicketPool and return the purchased ticket.
     *
     * @return The purchased ticket, or null if no ticket is available.
     */
    public Ticket buyTicket() {
        Ticket ticket = ticketPool.buyTicket();
        if (ticket != null) {
            logger.info("Ticket purchased successfully: {}", ticket);
        } else {
            logger.warn("No tickets available for purchase.");
        }
        return ticket;
    }

    /**
     * Retrieves the current count of tickets in the pool.
     * It fetches this count from the TicketPool and logs it.
     *
     * @return The number of tickets currently in the pool.
     */
    public int getTicketCount() {
        int count = ticketPool.getTicketCount();
        logger.info("Current ticket count in pool: {}", count);
        return count;
    }
}