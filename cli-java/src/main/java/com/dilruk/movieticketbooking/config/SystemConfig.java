package com.dilruk.movieticketbooking.config;

import com.dilruk.movieticketbooking.util.ConsoleUtil;
import com.dilruk.movieticketbooking.util.Logging;

import java.util.Scanner;

/**
 * This class manages the configuration settings for the movie ticket booking simulation.
 * It provides methods to retrieve and set the following configuration parameters:
 * * Total number of tickets available in the system
 * * Ticket release rate by vendors (tickets released per second)
 * * Customer retrieval rate (tickets attempted to purchase per second)
 * * Maximum capacity of the ticket pool (should be less than totalTickets to avoid unnecessary memory overhead)
 * * Number of vendors participating in the simulation
 * * Number of customers participating in the simulation
 */
public class SystemConfig {

    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;
    private static int noOfVendors;
    private static int noOfCustomers;

    /**
     * Retrieves the total number of tickets available in the system.
     *
     * @return The total number of tickets.
     */
    public static int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Retrieves the rate at which vendors release tickets per second.
     *
     * @return The ticket release rate.
     */
    public static int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Retrieves the rate at which customers attempt to purchase tickets per second.
     *
     * @return The customer retrieval rate.
     */
    public static int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Retrieves the maximum capacity of the ticket pool.
     *
     * @return The maximum ticket capacity.
     */
    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    /**
     * Retrieves the number of vendors participating in the simulation.
     *
     * @return The number of vendors.
     */
    public static int getNoOfVendors() {
        return noOfVendors;
    }

    /**
     * Retrieves the number of customers participating in the simulation.
     *
     * @return The number of customers.
     */
    public static int getNoOfCustomers() {
        return noOfCustomers;
    }

    /**
     * Prompts the user for configuration settings and saves them to the corresponding fields.
     * Utilizes ConsoleUtil for user interaction and Logging for recording the configuration.
     */
    public static void addSimulationConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=========================================");
        System.out.println("              Configuration              ");
        System.out.println("=========================================\n");

        SystemConfig.totalTickets = ConsoleUtil.promptForPositiveInteger(scanner, "Set total amount of tickets: ");

        SystemConfig.ticketReleaseRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set ticket release rate (per second): ");

        SystemConfig.customerRetrievalRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set customer retrieval rate (per second): ");

        SystemConfig.maxTicketCapacity = ConsoleUtil.promptForPositiveInteger(scanner, "Set maximum ticket capacity: ");

        SystemConfig.noOfVendors = ConsoleUtil.promptForPositiveInteger(scanner, "Set number of vendors: ");

        SystemConfig.noOfCustomers = ConsoleUtil.promptForPositiveInteger(scanner, "Set number of customers: ");

        SystemConfig.displaySummary();
    }

    /**
     * Displays a summary of the current configuration settings to the console and log file.
     * Utilizes Logging for both printing and logging the summary.
     */
    private static void displaySummary() {
        Logging.printlnAndLog("\n=== Configuration Summary ===\n");
        Logging.printlnAndLog("=============================");
        Logging.printlnAndLog(" Total tickets            : " + totalTickets);
        Logging.printlnAndLog(" Ticket release rate      : " + ticketReleaseRate);
        Logging.printlnAndLog(" Customer retrieval rate  : " + customerRetrievalRate);
        Logging.printlnAndLog(" Maximum ticket capacity  : " + maxTicketCapacity);
        Logging.printlnAndLog(" Number of vendors        : " + noOfVendors);
        Logging.printlnAndLog(" Number of customers      : " + noOfCustomers);
        Logging.printlnAndLog("=============================\n");
    }
}
