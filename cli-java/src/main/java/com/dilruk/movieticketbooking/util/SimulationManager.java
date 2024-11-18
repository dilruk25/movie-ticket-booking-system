package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.consumer.Customer;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;

public class SimulationManager {

    // To stop the simulation process
    private static boolean isRunning = false;
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int totalTickets;
    private final int ticketReleaseRate;
    Thread vendorThread;
    Thread customerThread;

    public SimulationManager(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = SystemConfig.getCustomerRetrievalRate();
        this.totalTickets = SystemConfig.getTotalTickets();
        this.ticketReleaseRate = SystemConfig.getTicketReleaseRate();
    }

    /**
     * Retrieves the current state of the simulation (running or stopped).
     *
     * @return true if the simulation is running, otherwise false.
     */
    public static boolean getIsRunning() {
        return SimulationManager.isRunning;
    }

    // Setters
    public static void setIsRunning(boolean isRunning) {
        SimulationManager.isRunning = isRunning;
    }

    public void startSimulation() {
        if (isRunning) {
            System.out.println("----------------------------------------");
            System.out.println(" Simulation is already running.");
            System.out.println("----------------------------------------\n");
            return;
        }
        System.out.println("\n----------------------------------------");
        System.out.println(" Configuring the system...");
        System.out.println(" Starting the simulation...");
        System.out.println("----------------------------------------");

        vendorThread = new Thread(new Vendor(ticketPool, totalTickets, ticketReleaseRate));
        customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate));

        vendorThread.start();
        customerThread.start();
        System.out.println("Successfully Started the simulation...");
    }

    public void stopSimulation() {
        System.out.println("Stopping the simulation...");

        if (vendorThread != null) {
            vendorThread.interrupt();
        }
        if (customerThread != null) {
            customerThread.interrupt();
        }


        // Wait for threads to finish
        try {
            if (vendorThread != null) {
                vendorThread.join();
            }
            if (customerThread != null) {
                customerThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Simulation stopping interrupted.");
        }

        System.out.println("Simulation stopped.");
    }
}