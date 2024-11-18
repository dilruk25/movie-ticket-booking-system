package com.dilruk.movieticketbooking.config;

import com.dilruk.movieticketbooking.Main;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.ConsoleUtil;
import com.dilruk.movieticketbooking.util.SimulationManager;

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

    public static int getTotalTickets() {
        return SystemConfig.totalTickets;
    }

    public static int getTicketReleaseRate() {
        return SystemConfig.ticketReleaseRate;
    }

    public static int getCustomerRetrievalRate() {
        return SystemConfig.customerRetrievalRate;
    }

    public static int getMaxTicketCapacity() {
        return SystemConfig.maxTicketCapacity;
    }

    public static void addSimulationConfigurations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=========================================");
        System.out.println("              Configuration              ");
        System.out.println("=========================================\n");

//        SystemConfig.totalTickets = 100;
//
//        SystemConfig.ticketReleaseRate = 1;
//
//        SystemConfig.customerRetrievalRate =1 ;
//
//        SystemConfig.maxTicketCapacity = 500;

                SystemConfig.totalTickets = ConsoleUtil.promptForPositiveInteger(scanner, "Set total amount of tickets: ");

                SystemConfig.ticketReleaseRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set ticket release rate (per second): ");

                SystemConfig.customerRetrievalRate = ConsoleUtil.promptForPositiveInteger(scanner, "Set customer retrieval rate (per second): ");

                SystemConfig.maxTicketCapacity = ConsoleUtil.promptForPositiveInteger(scanner, "Set maximum ticket capacity: ");

                SystemConfig.displaySummary();
    }

    /**
     * Displays a summary of the current configuration.
     */
    private static void displaySummary() {
        System.out.println("\n=== Configuration Summary ===\n");
        System.out.println("Total tickets: " + totalTickets);
        System.out.println("Ticket release rate: " + ticketReleaseRate);
        System.out.println("Customer retrieval rate: " + customerRetrievalRate);
        System.out.println("Maximum ticket capacity: " + maxTicketCapacity);
        System.out.println("=============================\n");
    }

    /**
     * Handles user commands to confirm configuration setup.
     *
     * @param scanner Input scanner.
     */
    public static void startConfirmation(Scanner scanner) {
        while (true) {
            System.out.println("Type 'start' to begin simulation, 'menu' to return to the main menu, or 'exit' to quit.");
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "start":
                    TicketPool ticketPool = new TicketPool();
                    SimulationManager simulationManager = new SimulationManager(ticketPool);
                    simulationManager.startSimulation();
                    break;
                case "menu":
                    Main.menu();
                    break;
                case "exit":
                    System.out.println("Exiting system...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("----------------------------------------");
                    System.out.println("Invalid command. Please enter 'start', 'menu', or 'exit'.");
                    System.out.println("----------------------------------------\n");
            }
        }
    }
}
