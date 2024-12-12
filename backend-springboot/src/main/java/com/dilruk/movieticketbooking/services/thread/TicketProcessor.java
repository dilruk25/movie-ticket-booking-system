package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.models.event.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketProcessor {

    private final TicketPool ticketPool;

    public TicketProcessor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Method to add tickets to the pool (called by VendorThread)
    public void addTickets(int numberOfTickets) {
        for (int i = 0; i < numberOfTickets; i++) {
            ticketPool.addTicket(Ticket); // Assuming each ticket is represented by an integer (id)
        }
    }

    // Method to remove a ticket from the pool (called by CustomerThread)
    public Ticket removeTicket() {
        return ticketPool.removeTicket();
    }

    // Method to get the current count of tickets in the pool
    public int getTicketCount() {
        return ticketPool.getTicketCount();
    }
}
