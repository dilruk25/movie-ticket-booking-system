package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Customer;
import com.dilruk.movieticketbooking.model.TicketPool;
import com.dilruk.movieticketbooking.model.Vendor;

public class SimulationManager implements Runnable {

    // Vendors' ticket release count per day
    private static int ticketReleaseRatePerSecond;
    // Customers' ticket purchase count per day
    private static int customerRetrievalRatePerSecond;

    public SimulationManager() {
        ticketReleaseRatePerSecond = SystemConfig.getTicketReleaseRatePerSecond();
        customerRetrievalRatePerSecond = SystemConfig.getCustomerRetrievalRatePerSecond();
        SimulationManager.startTicketPoolSimulation(100); // Initialize the ticket pool with a certain capacity
    }

    public static void startSimulation() {
        System.out.println("\nSimulation has successfully started");

        SystemConfig.addSimulationConfigurations();
        System.out.println("\nConfigurations have added successfully");
        SimulationManager.createVendorsForSimulation(5); // Assign 5 vendors by default
        SimulationManager.startTicketPoolSimulation(5); // Assign 5 vendors by default (Read line 37, 38)
        SimulationManager.startCustomerSimulation(100); // Assign customer count by default

    }

    public static void createVendorsForSimulation(int vendorCount) {
        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor();
        }
    }

    // Consider each vendor creates ticket pool for each movie
    // Consider there's only one movie event has for all users to reduce complexity for now
    public static void startTicketPoolSimulation(int vendorCount) {
        TicketPool ticketPool = new TicketPool();
    }

    public static void startCustomerSimulation(int customerCount) {
        Customer customer = new Customer();
    }

    @Override
    public void run() {

    }
}
