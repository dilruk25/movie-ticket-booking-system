package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.pools.TicketPool;
import com.dilruk.movieticketbooking.models.ticket.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A thread class representing a customer attempting to purchase tickets from the ticket pool.
 * This thread will continuously attempt to buy tickets until it is interrupted or stopped.
 */
public class CustomerThread extends Thread {

    // Atomic flag to indicate whether the thread is running
    public static final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TicketProcessor ticketProcessor;
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    /**
     * Constructor to initialize the customer thread with the system configuration and ticket processor.
     *
     * @param systemConfig The system configuration containing ticket retrieval rate.
     * @param ticketProcessor The processor responsible for managing ticket purchase and processing.
     */
    public CustomerThread(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
        this.ticketPool = ticketProcessor.getTicketPool();
        this.customerRetrievalRate = systemConfig.getCustomerRetrievalRate();
    }

    /**
     * The main method that runs when the thread is started. It attempts to purchase a ticket
     * from the pool, waits if no tickets are available, and sleeps for a defined retrieval rate
     * before trying again.
     */
    @Override
    public void run() {
        try {
            // Use synchronized block to ensure thread safety while accessing ticketPool
            synchronized (ticketPool) {
                Ticket ticket = ticketProcessor.buyTicket();

                // Check if a ticket was purchased successfully
                if (ticket != null) {
                    logger.info("Customer purchased ticket: {}", ticket);
                } else {
                    // If no ticket is available, log and wait for a new ticket to be added
                    logger.info("No tickets available for purchase. Waiting...");
                    ticketPool.wait(); // Wait for tickets to be available
                }
            }

            // Simulate the customer retrieval rate before trying again
            Thread.sleep(customerRetrievalRate);
        } catch (InterruptedException e) {
            // Handle interruption and log the error
            logger.error("Customer thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            // Mark the thread as not running when finished
            isRunning.set(false);
            logger.info("Customer thread stopped.");
        }
    }
}
