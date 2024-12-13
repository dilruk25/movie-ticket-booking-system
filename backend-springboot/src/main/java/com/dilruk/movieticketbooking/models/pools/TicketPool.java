package com.dilruk.movieticketbooking.models.pools;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import com.dilruk.movieticketbooking.repositories.TicketRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TicketPool {

    private final Lock lock = new ReentrantLock();
    private final TicketRepository ticketRepository;
    private final int maxTicketCapacity;
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;

    public TicketPool(SystemConfig systemConfig, TicketRepository ticketRepository) {
        this.maxTicketCapacity = systemConfig.getMaxTicketCapacity();
        this.totalTickets = systemConfig.getTotalTickets();
        this.ticketReleaseRate = systemConfig.getTicketReleaseRate();
        this.customerRetrievalRate = systemConfig.getCustomerRetrievalRate();
        this.ticketRepository = ticketRepository;
    }

    /**
     * Adds a ticket to the pool if there is available capacity.
     * Saves the ticket to the database.
     *
     * @param ticket The ticket to add.
     * @return true if the ticket was added, false if capacity is reached.
     */
    public boolean addTicket(Ticket ticket) {
        lock.lock();
        try {
            if (getTicketCount() < maxTicketCapacity) {
                ticketRepository.save(ticket); // Save ticket to the database
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a ticket from the pool (buy ticket).
     * Deletes the ticket from the database.
     *
     * @return The removed ticket, or null if the pool is empty.
     */
    public Ticket buyTicket() {
        lock.lock();
        try {
            Ticket ticket = ticketRepository.findTopByOrderByIdAsc().orElse(null);
            if (ticket != null) {
                ticketRepository.delete(ticket);
            }

            return ticket;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets the current count of tickets in the pool.
     *
     * @return The number of tickets currently in the pool.
     */
    public int getTicketCount() {
        lock.lock();
        try {
            return (int) ticketRepository.count();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the pool has available capacity for new tickets.
     *
     * @return true if there is available capacity, false otherwise.
     */
    public boolean hasCapacity() {
        lock.lock();
        try {
            return getTicketCount() < maxTicketCapacity;
        } finally {
            lock.unlock();
        }
    }
}