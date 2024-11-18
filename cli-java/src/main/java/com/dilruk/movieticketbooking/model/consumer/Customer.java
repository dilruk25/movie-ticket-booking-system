package com.dilruk.movieticketbooking.model.consumer;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.pool.TicketPool;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate;

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
                Thread.sleep(1000/customerRetrievalRate);
            } catch (InterruptedException e) {
                System.out.println("Interruption happened. Aborting ticket purchases...");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Finished ticket purchases");
    }
}
