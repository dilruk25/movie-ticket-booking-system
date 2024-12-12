package com.dilruk.movieticketbooking.model.consumer;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;
import com.dilruk.movieticketbooking.util.Logging;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a customer in the movie ticket booking simulation. Customers attempt to purchase tickets from the shared ticket pool at a specified rate.
 */
public class Customer implements Runnable {

    /**
     * Indicates whether all customers have finished their ticket purchasing attempts.
     */
    public static volatile AtomicBoolean isCustomerFinished = new AtomicBoolean(false);

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    /**
     * Constructs a new Customer instance.
     *
     * @param ticketPool The shared ticket pool from which customers purchase tickets.
     */
    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = SystemConfig.getCustomerRetrievalRate();
    }

    /**
     * The main execution loop for the customer.
     * <p>
     * 1. Checks if all vendors have finished and all tickets have been sold.
     * 2. Attempts to purchase a ticket from the ticket pool if available.
     * 3. Sleeps for a specified duration based on the customer retrieval rate.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !isCustomerFinished.get()) {
            try {

                if (Vendor.getIsVendorFinished().get() && ticketPool.getAvailableTicketList().isEmpty() && SystemConfig.getTotalTickets() == Ticket.getTicketCount().get()) {
                    Logging.log("----------------------------------------");
                    Logging.log("All tickets has been sold.");
                    Logging.log("----------------------------------------\n");
                    isCustomerFinished.set(true);
                    SimulationManager.setIsRunning(new AtomicBoolean(false));
                    Logging.close();
                    return;
                }

                if (!ticketPool.getAvailableTicketList().isEmpty()) {
                    ticketPool.buyTicket();
                }

                Thread.sleep(1000 / customerRetrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logging.log("Customer threads have been manually interrupted.");
                break;
            }
        }
    }
}
