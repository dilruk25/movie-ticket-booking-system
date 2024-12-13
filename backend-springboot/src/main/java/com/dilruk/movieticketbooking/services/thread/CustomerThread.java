package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.pools.TicketPool;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerThread extends Thread {

    public static final AtomicBoolean isRunning = new AtomicBoolean(false);

    private static final Logger logger = LoggerFactory.getLogger(CustomerThread.class);
    private final TicketProcessor ticketProcessor;
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    public CustomerThread(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
        this.ticketPool = ticketProcessor.getTicketPool();
        this.customerRetrievalRate = systemConfig.getCustomerRetrievalRate();
    }

    @Override
    public void run() {
        try {
            // Access ticketPool with thread-safe
            synchronized (ticketPool) {
                Ticket ticket = ticketProcessor.buyTicket();
                if (ticket != null) {
                    logger.info("Customer purchased ticket: {}", ticket);
                } else {
                    logger.info("No tickets available for purchase. Waiting...");
                    wait();
                }
            }
            Thread.sleep(customerRetrievalRate);
        } catch (InterruptedException e) {
            logger.error("Customer thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            isRunning.set(false);
            logger.info("Customer thread stopped.");
        }
    }
}