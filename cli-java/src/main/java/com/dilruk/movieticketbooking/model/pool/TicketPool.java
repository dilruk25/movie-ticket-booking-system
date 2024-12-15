package com.dilruk.movieticketbooking.model.pool;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.producer.Vendor;
import com.dilruk.movieticketbooking.util.Logging;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Manages the pool of available movie tickets.
 */
public class TicketPool {

    private final int totalTickets;
    private final int maxTicketCapacity;
    private final Queue<Ticket> availableTicketList;

    /**
     * Creates a new TicketPool instance.
     */
    public TicketPool() {
        this.totalTickets = SystemConfig.getTotalTickets();
        this.maxTicketCapacity = SystemConfig.getMaxTicketCapacity();
        this.availableTicketList = new LinkedList<>();
    }

    /**
     * Checks if the ticket pool is empty.
     *
     * @return True if the ticket pool is empty, False otherwise.
     */
    public synchronized boolean isEmpty() {
        return availableTicketList.isEmpty();
    }

    /**
     * Adds a new ticket to the pool, waiting if the pool is full.
     */
    public synchronized void addTicket() {

        while (availableTicketList.size() >= maxTicketCapacity) {
            Logging.log("----------------------------------------");
            Logging.log(" Maximum ticket capacity has reached: " + this.maxTicketCapacity);
            Logging.log(" Waiting for a ticket purchase...");
            Logging.log("----------------------------------------\n");

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logging.log("Vendor thread interrupted while waiting.");
                return;
            }
        }

        Ticket ticket = new Ticket(); // Creates ticket assigning random values

        Logging.log("----------------------------------------");
        Logging.log(" Total tickets created: " + Ticket.getTicketCount().get());

        availableTicketList.add(ticket);

        Logging.log(" [" + Thread.currentThread().getName() + "]" + " added: " + ticket);
        Logging.log(" Available tickets: " + availableTicketList.size());
        Logging.log("----------------------------------------\n");

        if(Ticket.getTicketCount().get() == totalTickets) {
            Vendor.isVendorFinished.set(true);
        }
        notify();
    }

    /**
     * Removes and returns a ticket from the pool, waiting if the pool is empty.
     **/
    public synchronized void buyTicket() {
        while (availableTicketList.isEmpty()) {
            if (Vendor.isVendorFinished.get()) {
                return;
            }
            Logging.log("\n----------------------------------------");
            Logging.log(" No Tickets available. Waiting ...");
            Logging.log("----------------------------------------\n");

            try {
                wait(); // Wait until a ticket is added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logging.log("Customer thread interrupted while waiting.");
                return;
            }
        }

        // To display the removed ticket from the list
        Ticket boughtTicket = availableTicketList.poll();

        Logging.log("----------------------------------------");
        Logging.log(" [" + Thread.currentThread().getName() + "]" + " bought: " + boughtTicket);
        Logging.log(" Available tickets: " + availableTicketList.size());
        Logging.log("----------------------------------------\n");

        notify(); // Notify threads waiting to add tickets
    }
}