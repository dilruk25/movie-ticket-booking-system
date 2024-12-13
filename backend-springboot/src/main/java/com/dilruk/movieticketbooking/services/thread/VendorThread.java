package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Vendor thread responsible for adding tickets to the ticket pool.
 * This thread will continue running until interrupted or stopped.
 */
public class VendorThread extends Thread {

    public static AtomicBoolean isRunning = new AtomicBoolean(false);
    public static AtomicInteger soldTicketCount = new AtomicInteger(0);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TicketProcessor ticketProcessor;
    private final int ticketReleaseRate;

    /**
     * Constructor to initialize the vendor thread with system configuration and ticket processor.
     *
     * @param systemConfig    The system configuration containing vendor-specific settings.
     * @param ticketProcessor The processor responsible for adding tickets.
     */
    public VendorThread(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
        this.ticketReleaseRate = systemConfig.getTicketReleaseRate();
    }

    @Override
    public void run() {
        isRunning.set(true);
        logger.info("Vendor thread started.");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Add tickets to the pool
                boolean ticketAdded = ticketProcessor.addTickets();

                if (ticketAdded) {
                    soldTicketCount.incrementAndGet();
                    logger.info("Ticket added successfully. Total sold tickets: {}", soldTicketCount.get());
                } else {
                    logger.info("No tickets added in this cycle.");
                }

                Thread.sleep(ticketReleaseRate); // Delay time
            }
        } catch (InterruptedException e) {
            // Log and handle thread interruption
            logger.warn("Vendor thread interrupted.", e);
            Thread.currentThread().interrupt(); // Restore interrupt flag
        } finally {
            isRunning.set(false);
            logger.info("Vendor thread stopped.");
        }
    }
}