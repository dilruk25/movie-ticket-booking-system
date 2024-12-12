package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.models.event.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerThread extends Thread {

    public static AtomicBoolean isRunning = new AtomicBoolean(false);

    private static final Logger logger = LoggerFactory.getLogger(CustomerThread.class);
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int customerRetrievalRate;

    public CustomerThread(SystemConfig systemConfig, TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = systemConfig.getCustomerRetrievalRate();
        this.totalTickets = systemConfig.getTotalTickets();
    }

    @Override
    public void run() {
        isRunning = new AtomicBoolean(true);

        while (isRunning.get()) {
            Ticket ticket = ticketPool.removeTicket();
            if (ticket != null) {
                logger.info("Customer purchased {}", ticket);
            } else {
                logger.info("No tickets available for purchase");
            }
            try {
                Thread.sleep(1500); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                isRunning = new AtomicBoolean(false);
            }
        }
    }
}