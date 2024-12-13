package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for managing the threads related to the ticket processing.
 * It can start vendor and customer threads for adding and removing tickets from the pool.
 */
@Service
public class ThreadManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadManagementService.class);

    private final TicketProcessor ticketProcessor;

    /**
     * Constructor to initialize the ThreadManagementService with the TicketProcessor.
     *
     * @param ticketProcessor The processor that manages ticket operations.
     */
    public ThreadManagementService(TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
    }

    /**
     * Starts the Vendor thread which will add tickets to the pool.
     * This thread will keep running and adding tickets at regular intervals.
     *
     * @param systemConfig Configuration for ticket system, used for vendor thread settings.
     */
    public void startVendorThread(SystemConfig systemConfig) {
        try {
            VendorThread vendorThread = new VendorThread(systemConfig, ticketProcessor);
            vendorThread.start();
            logger.info("Vendor thread started successfully.");
        } catch (Exception e) {
            logger.error("Error starting vendor thread", e);
        }
    }

    /**
     * Starts the Customer thread which will remove tickets from the pool.
     * This thread will run until there are no tickets available to purchase.
     *
     * @param systemConfig Configuration for ticket system, used for customer thread settings.
     */
    public void startCustomerThread(SystemConfig systemConfig) {
        try {
            CustomerThread customerThread = new CustomerThread(systemConfig, ticketProcessor);
            customerThread.start();
            logger.info("Customer thread started successfully.");
        } catch (Exception e) {
            logger.error("Error starting customer thread", e);
        }
    }

    /**
     * Stops both the vendor and customer threads gracefully.
     */
    public void stopThreads() {
        // Implement logic to stop both threads gracefully (e.g., by setting flags or interrupting)
        if (VendorThread.isRunning.get()) {
            VendorThread.isRunning.set(false);
            logger.info("Vendor thread stopped.");
        }

        if (CustomerThread.isRunning.get()) {
            CustomerThread.isRunning.set(false);
            logger.info("Customer thread stopped.");
        }
    }
}
