package com.dilruk.movieticketbooking.model.pool;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.util.LogUtil;

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
            LogUtil.log("----------------------------------------");
            LogUtil.log(" Maximum ticket capacity has reached: " + this.maxTicketCapacity);
            LogUtil.log(" Waiting for a ticket purchase...");
            LogUtil.log("----------------------------------------\n");

            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Ticket ticket = new Ticket(); // Creates ticket assigning random values

        LogUtil.log("----------------------------------------");
        LogUtil.log(" Total tickets created: " + Ticket.getTicketCount().get());

        availableTicketList.add(ticket);

        LogUtil.log(" [" + Thread.currentThread().getName() + "]" + " added: " + ticket);
        LogUtil.log(" Available tickets: " + availableTicketList.size());
        LogUtil.log("----------------------------------------\n");
        notifyAll();
    }

    public synchronized void buyTicket() {
        while (availableTicketList.isEmpty()) {
            LogUtil.log("\n----------------------------------------");
            LogUtil.log(" No Tickets available. Waiting ...");
            LogUtil.log("----------------------------------------\n");

            try {
                wait(); // Wait until a ticket is added
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // To display the removed ticket from the list
        Ticket boughtTicket = availableTicketList.poll();

        LogUtil.log("----------------------------------------");
        LogUtil.log(" [" + Thread.currentThread().getName() + "]" + " bought: " + boughtTicket);
        LogUtil.log(" Available tickets: " + availableTicketList.size());
        LogUtil.log("----------------------------------------\n");

        notifyAll(); // Notify threads waiting to add tickets
    }
}

