package com.dilruk.movieticketbooking.model.pool;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.util.Logging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TicketPool {

    private static final AtomicLong ticketPoolCount = new AtomicLong(0);
    // The total no of tickets available in the system
    private final int totalTickets;
    // Maximum capacity of the ticket pool
    // Always maxTicketCapacity should be less than the totalTickets to prevent from memory overhead
    private final int maxTicketCapacity;
    // Available Tickets are stored in an array
    private final Queue<Ticket> availableTicketList;


    public TicketPool() {
        ticketPoolCount.incrementAndGet();
        this.totalTickets = SystemConfig.getTotalTickets();
        this.maxTicketCapacity = SystemConfig.getMaxTicketCapacity();
        this.availableTicketList = new ConcurrentLinkedQueue<>();
    }

    // This method only use if we use multiple ticket pools in the system.
    public synchronized AtomicLong getTicketPoolCount() {
        return TicketPool.ticketPoolCount;
    }

    public synchronized Queue<Ticket> getAvailableTicketList() {
        return this.availableTicketList;
    }

    public synchronized void addTicket() {

        while (availableTicketList.size() >= maxTicketCapacity) {
            Logging.log("----------------------------------------");
            Logging.log(" Maximum ticket capacity has reached: " + this.maxTicketCapacity);
            Logging.log(" Waiting for a ticket purchase...");
            Logging.log("----------------------------------------\n");

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Ticket ticket = new Ticket(); // Creates ticket assigning random values

        Logging.log("----------------------------------------");
        Logging.log(" Total tickets created: " + Ticket.getTicketCount().get());

        availableTicketList.add(ticket);

        Logging.log(" [" + Thread.currentThread().getName() + "]" + " added: " + ticket);
        Logging.log(" Available tickets: " + availableTicketList.size());
        Logging.log("----------------------------------------\n");
        notifyAll();
    }

    public synchronized void buyTicket() {
        while (availableTicketList.isEmpty()) {
            Logging.log("\n----------------------------------------");
            Logging.log(" No Tickets available. Waiting ...");
            Logging.log("----------------------------------------\n");

            try {
                wait(); // Wait until a ticket is added
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // To display the removed ticket from the list
        Ticket boughtTicket = availableTicketList.poll();

        Logging.log("----------------------------------------");
        Logging.log(" [" + Thread.currentThread().getName() + "]" + " bought: " + boughtTicket);
        Logging.log(" Available tickets: " + availableTicketList.size());
        Logging.log("----------------------------------------\n");

        notifyAll(); // Notify threads waiting to add tickets
    }
}

