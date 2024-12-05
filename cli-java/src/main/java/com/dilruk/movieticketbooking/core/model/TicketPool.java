package com.dilruk.movieticketbooking.core.model;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.service.SimulationManager;

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
    // Use for assigning the exact SimulationManager object to TicketPool object
    private SimulationManager simulationManager;
    // Buying ticket count
    private AtomicLong buyingTicketCount = new AtomicLong(0);

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

    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }

    public synchronized void addTicket() {

        if (availableTicketList.size() > maxTicketCapacity) {
            System.out.println("----------------------------------------");
            System.out.println(" Maximum ticket capacity has reached: " + this.maxTicketCapacity);
            System.out.println(" Waiting for a ticket purchase...");
            System.out.println("----------------------------------------\n");

            return;
        }

        Ticket ticket = new Ticket(); // Creates ticket assigning random values

        System.out.println("----------------------------------------");

        availableTicketList.add(ticket);

        System.out.println("Ticket added: " + ticket);
        System.out.println("\nTotal tickets created: " + Ticket.getTicketCount().get());
        System.out.println("Available tickets: " + availableTicketList.size());
        System.out.println("----------------------------------------\n");
        notifyAll();
    }

    public synchronized void buyTicket() throws InterruptedException {
        if (availableTicketList.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.println("No Tickets available. Waiting ...");
            System.out.println("----------------------------------------\n");

            wait(); // Wait until a ticket is added
        }
        // To display the removed ticket from the list
        Ticket boughtTicket = availableTicketList.poll();
        buyingTicketCount.incrementAndGet();

        System.out.println("----------------------------------------");
        System.out.println("Buy ticket successfully: " + boughtTicket);
        System.out.println("\nBought tickets: " + buyingTicketCount);
        System.out.println("Available tickets: " + availableTicketList.size());
        System.out.println("----------------------------------------\n");

        notifyAll(); // Notify threads waiting to add tickets
    }
}

