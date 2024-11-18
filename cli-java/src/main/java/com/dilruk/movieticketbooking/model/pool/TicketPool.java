package com.dilruk.movieticketbooking.model.pool;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;

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

    public synchronized AtomicLong getTicketPoolCount() {
        return TicketPool.ticketPoolCount;
    }

    public synchronized void addTicket() {
        if (Ticket.getTicketCount().intValue() >= totalTickets) {
            System.out.println("----------------------------------------");
            System.out.println("Total ticket limit reached: " + this.totalTickets);
            System.out.println("----------------------------------------\n");

            return;
        }
        System.out.println(Ticket.getTicketCount().intValue());
        if (availableTicketList.size() > maxTicketCapacity) {
            System.out.println("----------------------------------------");
            System.out.println("Maximum ticket capacity has reached: " + this.maxTicketCapacity);
            System.out.println("----------------------------------------\n");

            return;
        }

        Ticket ticket = new Ticket();

        System.out.println("----------------------------------------");
        System.out.println("Ticket created: " + Ticket.getTicketCount().get());

        availableTicketList.add(ticket);

        System.out.println("Ticket added: " + ticket);
        System.out.println("Available tickets: " + availableTicketList.size());
        System.out.println("----------------------------------------\n");
        notifyAll();
    }

    public synchronized void buyTicket() throws InterruptedException {
        if (availableTicketList.isEmpty() && totalTickets == Ticket.getTicketCount().get()) {
            System.out.println("----------------------------------------");
            System.out.println("All tickets has been sold.");
            System.out.println("----------------------------------------\n");
            return;
        }
        if (availableTicketList.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.println("No Tickets available. Waiting ...");
            System.out.println("----------------------------------------\n");

            wait(); // Wait until a ticket is added
        }
        Ticket boughtTicket = availableTicketList.poll();

        System.out.println("----------------------------------------");
        System.out.println("Buy ticket successfully: " + boughtTicket);
        System.out.println("Available tickets: " + availableTicketList.size());
        System.out.println("----------------------------------------\n");

        notifyAll(); // Notify threads waiting to add tickets
    }
}

