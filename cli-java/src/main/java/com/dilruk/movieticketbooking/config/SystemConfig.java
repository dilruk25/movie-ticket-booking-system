package com.dilruk.movieticketbooking.config;

import com.dilruk.movieticketbooking.util.ConsoleUtil;

import java.util.Scanner;

public class SystemConfig {

    // The total no of tickets available in the system
    private static int totalTickets;
    // Vendors' ticket release count per second
    private static int ticketReleaseRate;
    // Customers' ticket purchase count per second
    private static int customerRetrievalRate;
    // Maximum capacity of the ticket pool (Should be less than totalTickets to avoid memory overhead)
    private static int maxTicketCapacity;
    // No of vendors in simulation
    private static int noOfVendors;
    // No of customers in simulation
    private static int noOfCustomers;

    public static int getTotalTickets() {
        return totalTickets;
    }

    public static int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public static int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public static int getNoOfVendors() {
        return noOfVendors;
    }

    public static int getNoOfCustomers() {
        return noOfCustomers;
    }

    public static void addSimulationConfig() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=========================================");
        System.out.println("              Configuration              ");
        System.out.println("=========================================\n");

//        SystemConfig.totalTickets = ConsoleUtil.promptForPositiveInteger(scanner, "Set total amount of tickets: ");
//
//        SystemConfig.ticketReleaseRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set ticket release rate (per second): ");
//
//        SystemConfig.customerRetrievalRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set customer retrieval rate (per second): ");
//
//        SystemConfig.maxTicketCapacity = ConsoleUtil.promptForPositiveInteger(scanner, "Set maximum ticket capacity: ");
//
//        SystemConfig.noOfVendors = ConsoleUtil.promptForPositiveInteger(scanner, "Set number of vendors: ");
//
//        SystemConfig.noOfCustomers = ConsoleUtil.promptForPositiveInteger(scanner, "Set number of customers: ");
//
//        SystemConfig.displaySummary();

        SystemConfig.totalTickets = 100; //TODO: REMOVE THIS
        SystemConfig.ticketReleaseRate = 10;
        SystemConfig.customerRetrievalRate = 9;
        SystemConfig.maxTicketCapacity = 30;
        SystemConfig.noOfVendors = 5;
        SystemConfig.noOfCustomers = 10;

    }

    /**
     * Displays a summary of the current configuration.
     */
    private static void displaySummary() {
        System.out.println("\n=== Configuration Summary ===\n");
        System.out.println("=============================");
        System.out.println(" Total tickets: " + totalTickets);
        System.out.println(" Ticket release rate: " + ticketReleaseRate);
        System.out.println(" Customer retrieval rate: " + customerRetrievalRate);
        System.out.println(" Maximum ticket capacity: " + maxTicketCapacity);
        System.out.println(" Number of vendors: " + noOfVendors);
        System.out.println(" Number of customers: " + noOfCustomers);
        System.out.println("=============================\n");
    }
}
