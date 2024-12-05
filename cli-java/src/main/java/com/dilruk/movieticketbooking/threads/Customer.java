package com.dilruk.movieticketbooking.threads;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.core.model.Ticket;
import com.dilruk.movieticketbooking.core.model.TicketPool;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    private String userName;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = SystemConfig.getCustomerRetrievalRate();
    }


    @Override
    public void run() {
        while (true) {
            try {
                ticketPool.buyTicket();

                if (ticketPool.getAvailableTicketList().isEmpty() && SystemConfig.getTotalTickets() == Ticket.getTicketCount().get()) {
                    System.out.println("----------------------------------------");
                    System.out.println("All tickets has been sold.");
                    System.out.println("----------------------------------------\n");
                    break;
                }
                Thread.sleep(1000/customerRetrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer threads have been manually interrupted.");
                break;
            }
        }
        System.out.println("\n----------------------------------------");
        System.out.println(" Finished ticket purchases");
        System.out.println(" customer threads are stopped.");
        System.out.println("----------------------------------------\n");
    }
}
