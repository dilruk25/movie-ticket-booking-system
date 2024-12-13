package com.dilruk.movieticketbooking.models.pools;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import com.dilruk.movieticketbooking.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a pool of movie tickets with capacity management.
 * Provides methods to add, retrieve, and check the ticket count in the pool.
 * Ensures thread-safe operations using a lock for concurrency control.
 */
@Component
public class TicketPool {

    private final Logger logger = LoggerFactory.getLogger(TicketPool.class);
    private final Lock lock = new ReentrantLock();  // Lock to ensure thread safety
    private final TicketRepository ticketRepository;  // Repository to interact with the database
    private final int maxTicketCapacity;  // Maximum capacity of the ticket pool
    private final int totalTickets;  // Total number of tickets to be made available initially
    private final int ticketReleaseRate;  // Rate at which tickets are released into the pool
    private final int customerRetrievalRate;  // Rate at which customers retrieve tickets from the pool

    /**
     * Constructs a TicketPool instance with the specified system configuration and ticket repository.
     *
     * @param systemConfig The configuration object containing system settings like ticket release rate and capacity.
     * @param ticketRepository The repository to interact with the ticket database.
     */
    public TicketPool(SystemConfig systemConfig, TicketRepository ticketRepository) {
        this.maxTicketCapacity = systemConfig.getMaxTicketCapacity();
        this.totalTickets = systemConfig.getTotalTickets();
        this.ticketReleaseRate = systemConfig.getTicketReleaseRate();
        this.customerRetrievalRate = systemConfig.getCustomerRetrievalRate();
        this.ticketRepository = ticketRepository;
    }

    /**
     * Adds a new ticket to the pool, if there is available capacity.
     * If capacity is reached, the ticket is not added, and the method returns false.
     * The ticket is saved to the database after being added to the pool.
     *
     * @param ticket The ticket to be added to the pool.
     * @return true if the ticket was successfully added, false if the pool has reached its capacity.
     */
    public boolean addTicket(Ticket ticket) {
        lock.lock();  // Lock the operation to ensure thread safety
        try {
            if (getTicketCount() < maxTicketCapacity) {
                ticketRepository.save(ticket);  // Save the ticket to the database
                return true;
            }
            return false;  // Return false if the pool is at maximum capacity
        } finally {
            lock.unlock();  // Release the lock
        }
    }

    /**
     * Allows a customer to buy a ticket from the pool.
     * The first available ticket is marked as unavailable (purchased) and returned.
     * If the pool is empty, null is returned.
     *
     * @return The purchased ticket, or null if there are no tickets available in the pool.
     */
    public Ticket buyTicket() {
        lock.lock();  // Lock the operation to ensure thread safety
        try {
            if (ticketRepository.count() != 0) {  // Ensure there are tickets available in the pool
                Optional<Ticket> ticket = ticketRepository.findTopByOrderByIdAsc();  // Get the first available ticket from the database
                if (ticket.isPresent()) {
                    ticket.get().setAvailability(false);  // Mark the ticket as unavailable (sold)
                    return ticket.get();
                }
            }
        } finally {
            lock.unlock();  // Release the lock
        }
        return null;  // Return null if no tickets are available
    }

    /**
     * Gets the current number of tickets available in the pool.
     *
     * @return The current number of tickets in the pool.
     */
    public int getTicketCount() {
        lock.lock();  // Lock the operation to ensure thread safety
        try {
            return (int) ticketRepository.count();  // Return the current count of tickets in the pool
        } finally {
            lock.unlock();  // Release the lock
        }
    }

    /**
     * Checks if there is available capacity in the pool for more tickets.
     * This is used to determine if more tickets can be added to the pool.
     *
     * @return true if the pool has capacity for more tickets, false otherwise.
     */
    public boolean hasCapacity() {
        lock.lock();  // Lock the operation to ensure thread safety
        try {
            return getTicketCount() < maxTicketCapacity;  // Check if current count is less than max capacity
        } finally {
            lock.unlock();  // Release the lock
        }
    }
}