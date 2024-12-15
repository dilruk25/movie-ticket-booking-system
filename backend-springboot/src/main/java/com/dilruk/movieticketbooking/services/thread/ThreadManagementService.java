package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This service is responsible for managing the threads related to the ticket processing.
 * It can start vendor and customer threads for adding and removing tickets from the pool.
 */
@Service
@RequiredArgsConstructor
public class ThreadManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadManagementService.class);

    private final TicketProcessor ticketProcessor;
    private final ConcurrentHashMap<String, Thread> customerThreads = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Thread> vendorThreads = new ConcurrentHashMap<>();

    private final AtomicInteger vendorThreadCounter = new AtomicInteger(0);
    private final AtomicInteger customerThreadCounter = new AtomicInteger(0);

    /**
     * Starts the Vendor thread which will add tickets to the pool.
     * This thread will keep running and adding tickets at regular intervals.
     *
     * @param systemConfig Configuration for ticket system, used for vendor thread settings.
     */
    public void startVendorThread(SystemConfig systemConfig) {
        try {
            vendorThreadCounter.incrementAndGet();
            String vendorName = "Vendor" + vendorThreadCounter.get();
            Thread vendorThread = new VendorThread(systemConfig, ticketProcessor);
            vendorThread.setName(vendorName);
            vendorThread.start();
            vendorThreads.put(vendorThread.getName(), vendorThread);
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
            customerThreadCounter.incrementAndGet();
            String customerName = "Customer" + customerThreadCounter.get();
            Thread customerThread = new CustomerThread(systemConfig, ticketProcessor);
            customerThread.setName(customerName);
            customerThread.start();
            customerThreads.put(customerName, customerThread);
            logger.info("Customer thread started successfully.");
        } catch (Exception e) {
            logger.error("Error starting customer thread", e);
        }
    }

    /**
     * Stops both the vendor and customer threads gracefully.
     */
    public void stopVendorThread() {
        for (Thread thread : vendorThreads.values()) {
            VendorThread.isRunning.set(false);
            thread.interrupt();
        }
        logger.info("Vendor thread stopped.");
    }

    public void stopCustomerThread() {
        for (Thread thread : customerThreads.values()) {
            CustomerThread.isRunning.set(false);
            thread.interrupt();
            logger.info("Customer thread stopped.");
        }
    }
}
