package com.dilruk.movieticketbooking.model.producer;

import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.SimulationManager;

public class Vendor implements Runnable {

    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {

        while (SimulationManager.getIsRunning()) {
            if (Ticket.getTicketCount().intValue() >= totalTickets) {
                System.out.println("----------------------------------------");
                System.out.println(" Total ticket limit reached: " + this.totalTickets);
                System.out.println(" Ticket adding cannot be proceed");
                System.out.println("----------------------------------------\n");

                break;
            }
            try {
                ticketPool.addTicket();
                Thread.sleep(1000/ticketReleaseRate);
            } catch (InterruptedException e) {
                System.out.println("\nVendor threads have been manually interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("\n----------------------------------------");
        System.out.println(" Finished adding tickets");
        System.out.println(" Vendor threads are stopped.");
        System.out.println("----------------------------------------\n");

    }
}
