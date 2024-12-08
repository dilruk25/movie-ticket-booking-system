package com.dilruk.movieticketbooking;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.exception.FileWritingException;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        PrintStream printStream = redirectOutputToFile();
        welcome();
        login();
        menu();

        System.setOut(printStream);
        scanner.close();
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
            System.out.println(" [3] Configure Simulation settings");
            System.out.println(" [4] Real-Time Monitoring");
            System.out.println("----------------------------------------");
            System.out.println(" [5] Configure web application settings");
            System.out.print(" [0] Exit\n> ");

            List<String> menuSelections = List.of("1", "2", "3", "4", "5", "0");

            String option = scanner.nextLine().trim();
//            String option = "1"; //TODO: REMOVE THIS

            if (!menuSelections.contains(option)) {
                System.out.println("----------------------------------------");
                System.out.println(" \"" + option + "\" is not a valid input");
                System.out.println("----------------------------------------\n");
                continue;
            }

            // Use instead of "if-else statement" for readability & efficiency
            switch (option) {
                case "1":
                    if (SimulationManager.getIsRunning()) {
                        System.out.println("----------------------------------------");
                        System.out.println(" Simulation is already running.");
                        System.out.println("----------------------------------------\n");
                    } else {
                        SystemConfig.addSimulationConfig(); // Configure settings
                        startConfirmation(); // Get confirmation to proceed simulation
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
            }
            return;
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
//            String input = "start"; //TODO: REMOVE THIS

            List<String> selections = List.of("start", "menu", "exit");

            if (!selections.contains(input)) {
                System.out.println("----------------------------------------");
                System.out.println(" Invalid command.");
                System.out.println(" Please enter 'start', 'menu', or 'exit'.");
                System.out.println("----------------------------------------\n");
                continue;
            }

            switch (input) {

                case "start":
                    TicketPool ticketPool = new TicketPool();
                    SimulationManager simulationManager = new SimulationManager(ticketPool);
                    simulationManager.startSimulation();
                    break;

                case "menu":
                    menu();
                    break;

                case "exit":
                    System.out.println(" Exiting system...");
                    System.exit(0);
                    break;
            }
            return;
        }
    }

    public static PrintStream redirectOutputToFile() {
        try {
            String logFilePath = "logs.txt";
            File logFile = new File(logFilePath);

            if (logFile.exists() && !logFile.delete()) {
                logger.warning("Existing file cannot be deleted: " + logFilePath);
                logger.warning("Overwriting existing file: " + logFilePath);
            } else {
                logger.info("Created new file: " + logFilePath);
            }

            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, false));
            return printStream;
        } catch (IOException e) {
            throw new FileWritingException(e.getMessage());
        }
    }
}
