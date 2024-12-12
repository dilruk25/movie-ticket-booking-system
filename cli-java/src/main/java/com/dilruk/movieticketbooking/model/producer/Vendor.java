package com.dilruk.movieticketbooking.model.producer;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.Logging;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a vendor in the movie ticket booking simulation. Vendors add tickets to the shared ticket pool at a specified rate.
 */
public class Vendor implements Runnable {

    /**
     * Represents a vendor in the movie ticket booking simulation. Vendors add tickets to the shared ticket pool at a specified rate.
     */
    public static volatile AtomicBoolean isVendorFinished = new AtomicBoolean(false);

    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;

    /**
     * Constructs a new Vendor instance.
     *
     * @param ticketPool The shared ticket pool to which vendors add tickets.
     */
    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.totalTickets = SystemConfig.getTotalTickets();
        this.ticketReleaseRate = SystemConfig.getTicketReleaseRate();
    }

    /**
     * Gets the flag indicating whether all vendors have finished.
     *
     * @return True if all vendors have finished, False otherwise.
     */
    public static AtomicBoolean getIsVendorFinished() {
        return isVendorFinished;
    }

    /**
     * The main execution loop for the vendor.
     * <p>
     * 1. Checks if the total ticket limit has been reached.
     * 2. Adds a new ticket to the ticket pool.
     * 3. Sleeps for a specified duration based on the ticket release rate.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !isVendorFinished.get()) {

            try {
                if (Ticket.getTicketCount().intValue() >= totalTickets) {
                    Logging.log("----------------------------------------");
                    Logging.log(" Total ticket limit reached: " + this.totalTickets);
                    Logging.log(" Ticket adding cannot be proceed");
                    Logging.log("----------------------------------------\n");
                    isVendorFinished.set(true);
                    return;
                }

                ticketPool.addTicket();
                Thread.sleep(1000 / ticketReleaseRate);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logging.log("\nVendor threads have been manually interrupted.");
                break;
            }
        }
    }
}
