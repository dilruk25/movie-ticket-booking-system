package com.dilruk.movieticketbooking;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.consumer.Customer;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;
import com.dilruk.movieticketbooking.util.FileHandlerUtil;
import com.dilruk.movieticketbooking.util.Logging;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.Objects;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static SimulationManager simulationManager = null;

    public static void main(String[] args) {
        welcome();
        login();
        menu();
    }

    /**
     * Displays the welcome screen.
     */
    public static void welcome() {
        System.out.println("=========================================");
        System.out.println("|             TicketCorner              |");
        System.out.println("=========================================\n");
        System.out.println(" Welcome to TicketCounter Admin Panel....\n");
    }

    /**
     * Handles user login with a maximum of 3 attempts.
     * After 3 attempts, need to rerun the program
     */
    public static void login() {
        // Has a default username and password for the CLI
        final String USERNAME = "admin";
        final String PASSWORD = "admin";

        // Remaining attempts
        int remainingAttempts = 3;

        System.out.println(" Please enter your credentials:\n");

        while (remainingAttempts > 0) {

            System.out.print(" Enter username: ");
            String username = scanner.nextLine();

            System.out.print(" Enter password: ");
            String password = scanner.nextLine();

            // Use for null safety instead of using username.equals(USERNAME)
            if (Objects.equals(username, USERNAME) && Objects.equals(password, PASSWORD)) {
                System.out.println("----------------------------------------");
                System.out.println("            Login Successful!           ");
                System.out.println("----------------------------------------");
                return;

            } else {
                remainingAttempts--; // Post increment
                System.out.println("----------------------------------------");
                System.out.println(" Username/password is incorrect.\n " + remainingAttempts + " attempts left.");
                System.out.println("----------------------------------------\n");
            }
        }
        System.out.println("----------------------------------------");
        System.out.println(" System has been locked.");
        System.out.println(" Please re-run the CLI");
        System.out.println("----------------------------------------");

        System.exit(0); // Exit the program with a normal status
    }

    /**
     * Displays the menu and handle user input.
     */
    public static void menu() {

        while (true) {
            System.out.println("\n=============== Main Menu ==============\n");

            System.out.println(" [1] Start simulation");
            System.out.println(" [2] Stop simulation");
            System.out.println(" [3] Real-Time Monitoring");
            System.out.println("----------------------------------------");
            System.out.print(" [0] Exit\n> ");

            String option = scanner.nextLine().trim();

            // Use instead of "if-else statement" for readability & efficiency
            switch (option) {
                case "1":
                    startSimulation();
                    break;
                case "2":
                    stopSimulation();
                    break;
                case "3":
                    monitorTickets();
                    break;
                case "0":
                    System.out.println("Exiting the CLI");
                    System.exit(0); // Exit the program with a normal status
                    return;
                default:
                    System.out.println("----------------------------------------");
                    System.out.println(" \"" + option + "\" is not a valid input");
                    System.out.println("----------------------------------------\n");
            }
        }
    }

    /**
     * Handles user commands to confirm configuration setup.
     */
    private static void startConfirmation() {
        while (true) {
            System.out.println(" Type 'start' to begin simulation, 'menu' to return to the main menu, or 'exit' to quit.");
            System.out.print(" Enter command: ");

            // Remove all leading and trailing spaces and converted to lowercase
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {

                case "start":
                    TicketPool ticketPool = new TicketPool();
                    simulationManager = new SimulationManager(ticketPool);
                    simulationManager.createSimulation();
                    break;

                case "menu":
                    menu();
                    break;

                case "exit":
                    System.out.println(" Exiting system...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("----------------------------------------");
                    System.out.println(" Invalid command.");
                    System.out.println(" Please enter 'start', 'menu', or 'exit'.");
                    System.out.println("----------------------------------------\n");
                    continue;
            }
            return;
        }
    }

    private static void startSimulation() {
        if (SimulationManager.getIsRunning().get()) {
            System.out.println("----------------------------------------");
            System.out.println(" Simulation is already running.");
            System.out.println("----------------------------------------\n");
        } else {
            Logging.initialize();
            SystemConfig.addSimulationConfig(); // Configure settings
            startConfirmation(); // Get confirmation to proceed simulation
        }
    }

    private static void stopSimulation() {
        if (!SimulationManager.getIsRunning().get()) {
            System.out.println("----------------------------------------");
            System.out.println(" Simulation is not running.");
            System.out.println("----------------------------------------\n");
            return;
        }

        if (Vendor.isVendorFinished.get()) {
            System.out.println("\n----------------------------------------");
            System.out.println(" Vendor threads has already stopped.");
            System.out.println("----------------------------------------");
        }

        if (Customer.isCustomerFinished.get()) {
            System.out.println("\n----------------------------------------");
            System.out.println(" Customer threads has already stopped.");
            System.out.println("----------------------------------------");
        } else {
            System.out.println("\n----------------------------------------");
            System.out.println(" Stopping simulation...");

            simulationManager.interruptSimulation();
        }

        System.out.println(" Simulation has stopped successfully.");
        System.out.println("----------------------------------------\n");
    }

    private static void monitorTickets() {
        FileHandlerUtil.readFile();
    }
}
