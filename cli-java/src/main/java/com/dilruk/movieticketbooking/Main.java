package com.dilruk.movieticketbooking;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main.welcome();
        Main.login();
        Main.menu();
        scanner.close();
    }

    /**
     * Displays the welcome screen.
     */
    public static void welcome() {
        System.out.println("=========================================");
        System.out.println("|             TicketCorner              |");
        System.out.println("=========================================\n");
        System.out.println("Welcome to TicketCounter Admin Panel....\n");
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

        System.out.println("Please enter your credentials:\n");

        while (true) {
            if (remainingAttempts == 0) {
                System.out.println("----------------------------------------");
                System.out.println(" System has been locked.");
                System.out.println(" Please re-run the CLI");
                System.out.println("----------------------------------------");
                return;
            }

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Use for null safety instead of using username.equals(USERNAME)
            if (Objects.equals(username, USERNAME) && Objects.equals(password, PASSWORD)) {
                System.out.println("----------------------------------------");
                System.out.println("            Login Successful!           ");
                System.out.println("----------------------------------------\n");
                return;

            }
            if (remainingAttempts > 0) {
                remainingAttempts--; // Post increment
                System.out.println("----------------------------------------");
                System.out.println(" Username/password is incorrect.\n " + remainingAttempts + " attempts left.");
                System.out.println("----------------------------------------\n");
            }
        }
    }

    /**
     * Displays the menu and handle user input.
     */
    public static void menu() {

        while (true) {
            System.out.println("\n=============== Main Menu ==============\n");

            System.out.println("[1] Start simulation");
            System.out.println("[2] Stop simulation");
            System.out.println("[3] Configure Simulation settings");
            System.out.println("[4] Real-Time Monitoring");
            System.out.println("----------------------------------------");
            System.out.println("[5] Configure web application settings");
            System.out.print("[0] Exit\n> ");

            String option = scanner.nextLine().trim();


            // Use instead of "if-else statement" for readability & efficiency
            switch (option) {
                case "1":
                    if (SimulationManager.getIsRunning()) {
                        System.out.println("----------------------------------------");
                        System.out.println(" Simulation is already running.");
                        System.out.println("----------------------------------------\n");
                    } else {
                        System.out.println("----------------------------------------");
                        System.out.println(" Starting simulation...");
                        System.out.println("----------------------------------------\n");

                        SystemConfig.addSimulationConfig(); // Configure settings
                        startConfirmation(); // Confirm to proceed simulation
                    }
                    break;
                case "2":
                    if (!SimulationManager.getIsRunning()) {
                        System.out.println("----------------------------------------");
                        System.out.println(" Simulation is not running.");
                        System.out.println("----------------------------------------");
                    } else {
                        System.out.println("----------------------------------------");
                        System.out.println(" Stopping simulation...");
                        System.out.println("----------------------------------------\n");

                        // TODO: need to fix here
                        // simulationManager.stopSimulation(); // Stop the simulation
                    }
                    break;
                case "3":
                    System.out.println("Simulation configuration settings has opened");
                    break;
                case "4":
                    System.out.println("Simulation is being monitored");
                    break;
                case "5":
                    System.out.println("Web Configuration has opened");
                    SystemConfig.addSimulationConfig();
                    break;
                case "0":
                    System.out.println("You have exit from the cli");
                    System.exit(0); // Exit the program with a normal status
                    break;
                default:
                    System.out.println("----------------------------------------");
                    System.out.println(" " + option + " is not a valid input");
                    System.out.println("----------------------------------------\n");
                    break;
            }
        }
    }

    /**
     * Handles user commands to confirm configuration setup.
     */
    private static void startConfirmation() {
        while (true) {
            System.out.println("Type 'start' to begin simulation, 'menu' to return to the main menu, or 'exit' to quit.");
            System.out.print("Enter command: ");

            // Remove all leading and trailing spaces and converted to lowercase
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {

                case "start":
                    TicketPool ticketPool = new TicketPool();
                    SimulationManager simulationManager = new SimulationManager(ticketPool);
                    simulationManager.startSimulation();
                    return;

                case "menu":
                    Main.menu();
                    return;

                case "exit":
                    System.out.println("Exiting system...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("----------------------------------------");
                    System.out.println("Invalid command.");
                    System.out.println("Please enter 'start', 'menu', or 'exit'.");
                    System.out.println("----------------------------------------\n");
            }
        }
    }


}
