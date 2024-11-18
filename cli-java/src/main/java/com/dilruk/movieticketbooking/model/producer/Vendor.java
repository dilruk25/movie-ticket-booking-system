package com.dilruk.movieticketbooking.model.producer;

import com.dilruk.movieticketbooking.model.pool.TicketPool;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {

        while (true) {
            try {
                ticketPool.addTicket();
                Thread.sleep(1000/ticketReleaseRate);
            } catch (InterruptedException e) {
                System.out.println("Interruption happened. Aborting ticket purchases...");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Finished adding tickets");
    }
}
