package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.consumer.Customer;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {

    // To stop the simulation process
    private static boolean isRunning = false;
    private final TicketPool ticketPool;
    private final int noOfVendors;
    private final int noOfCustomers;
    private final List<Thread> vendorList = new ArrayList<>();
    private final List<Thread> customerList = new ArrayList<>();

    public SimulationManager(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.ticketPool.setSimulationManager(this); // To use the very simulationManager object in ticketPool

        this.noOfVendors = SystemConfig.getNoOfVendors();
        this.noOfCustomers = SystemConfig.getNoOfCustomers();
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

        SimulationManager.setIsRunning(true);

        System.out.println(" Starting the simulation...");
        System.out.println(" Creating vendor threads...");
        System.out.println(" Creating customer threads...");
        System.out.println("----------------------------------------\n");

        for (int i = 1; i <= noOfVendors; i++) {
            Thread vendorThread = new Thread(new Vendor(ticketPool), "Vendor " + i);
            vendorThread.start();
            vendorList.add(vendorThread);
        }

        for (int i = 1; i <= noOfCustomers; i++) {
            Thread customerThread = new Thread(new Customer(ticketPool), "Customer " + i);
            customerThread.start();
            customerList.add(customerThread);
        }
    }

    public void stopSimulation() {
        System.out.println("\n----------------------------------------");
        System.out.println("Stopping the simulation...");

            if (!vendorList.isEmpty()) {
                for (Thread thread : vendorList) {
                    thread.interrupt();
                }
            }

            if (!customerList.isEmpty()) {
                for (Thread thread : customerList) {
                    thread.interrupt();
                }
            }

        try {

            if (!vendorList.isEmpty()) {
                for (Thread thread : vendorList) {
                    thread.join();
                }
            }

            if (!customerList.isEmpty()) {
                for (Thread thread : customerList) {
                    thread.join();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Simulation stopping interrupted.");
        }

        System.out.println(" Simulation has stopped successfully.");
        System.out.println("----------------------------------------\n");
    }
}