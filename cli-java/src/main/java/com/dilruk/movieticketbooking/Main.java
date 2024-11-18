package com.dilruk.movieticketbooking;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

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

        // Remaining attempts
        int remainingAttempts = 3;

        System.out.println("Please enter your credentials:\n");


        while (true) {
            if (remainingAttempts == 0) {
                System.out.println("----------------------------------------");
                System.out.println(" System has been locked.");
                System.out.println(" Please re-run the CLI");
                System.out.println("----------------------------------------\n");
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
            System.out.println("=============== Main Menu ==============\n");

            System.out.println("[1] Start simulation");
            System.out.println("[2] Stop simulation");
            System.out.println("[3] Configure Simulation settings");
            System.out.println("[4] Real-Time Monitoring");
            System.out.println("----------------------------------------");
            System.out.println("[5] Configure web application settings");
            System.out.print("[0] Exit\n> ");

            String option = scanner.nextLine();


            // Use instead of "if-else statement" for readability & efficiency
            switch (option) {
                case "1":
                    SystemConfig.addSimulationConfigurations();
                    SystemConfig.startConfirmation(scanner);
                    break;
                case "2":
                    SimulationManager.setIsRunning(false);
                    System.out.println("Simulation has successfully stopped");
                    System.exit(0);
                    break;
                case "3":
                    System.out.println("Simulation configuration settings has opened");
                    break;
                case "4":
                    System.out.println("Simulation is being monitored");
                    break;
                case "5":
                    System.out.println("Web Configuration has opened");
                    SystemConfig.addSimulationConfigurations();
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


}
