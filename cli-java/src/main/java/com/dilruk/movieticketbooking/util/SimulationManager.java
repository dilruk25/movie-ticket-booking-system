package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.consumer.Customer;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages the entire simulation process, including creating and starting vendor and customer threads.
 * It also provides methods to interrupt the simulation and check its current state.
 */
public class SimulationManager {

    // To stop the simulation process
    private static AtomicBoolean isRunning = new AtomicBoolean(false);
    private final TicketPool ticketPool;
    private final int noOfVendors;
    private final int noOfCustomers;
    private final List<Thread> vendorList = new ArrayList<>();
    private final List<Thread> customerList = new ArrayList<>();

    public SimulationManager(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.noOfVendors = SystemConfig.getNoOfVendors();
        this.noOfCustomers = SystemConfig.getNoOfCustomers();
    }

    /**
     * Retrieves the current state of the simulation (running or stopped).
     *
     * @return AtomicBoolean(true) if the simulation is running, otherwise AtomicBoolean(false).
     */
    public static AtomicBoolean getIsRunning() {
        return SimulationManager.isRunning;
    }

    /**
     * Set the current state of the simulation (running or stopped).
     */
    public static void setIsRunning(AtomicBoolean isRunning) {
        SimulationManager.isRunning = isRunning;
    }

    /**
     * Initializes the simulation by creating and starting vendor and customer threads.
     * <p>
     * The number of vendors and customers is determined by the configuration settings.
     * Each vendor and customer thread is assigned a unique name for identification.
     */
    public void createSimulation() {

        System.out.println("\n----------------------------------------");
        System.out.println(" Configuring the system...");

        SimulationManager.setIsRunning(new AtomicBoolean(true));

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

    /**
     * Interrupts the running simulation, stopping all vendor and customer threads.
     *<p>
     * This method sets the `isRunning` flag to `false`, interrupts each thread, and then waits for them to finish.
     */
    public void interruptSimulation() {
        isRunning.set(false);

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
                System.out.println("Stop Simulation has interrupted.");
            }
    }
}