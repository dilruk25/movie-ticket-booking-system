package com.dilruk.movieticketbooking.config;

import java.util.Scanner;

public class SystemConfig {

    // The total no of tickets available in the system when simulation starts
    private static int totalTickets;
    // Vendors' ticket release count per day
    private static int ticketReleaseRatePerSecond;
    // Customers' ticket purchase count per day
    private static int customerRetrievalRatePerSecond;
    // Maximum capacity of the ticket pool
    private static int maxTicketCapacity;

    // Getters
    public static int getTotalTickets() {
        return totalTickets;
    }

    public static int getTicketReleaseRatePerSecond() {
        return ticketReleaseRatePerSecond;
    }

    public static int getCustomerRetrievalRatePerSecond() {
        return customerRetrievalRatePerSecond;
    }

    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public static void addSimulationConfigurations() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("              Configuration              ");
        System.out.println("=========================================\n");

        try {
            System.out.print("Set initial amount of tickets : ");
            SystemConfig.totalTickets = Integer.parseInt(scanner.nextLine());

            System.out.print("Set ticket release rate : ");
            SystemConfig.ticketReleaseRatePerSecond = Integer.parseInt(scanner.nextLine());

            System.out.print("Set customer retrieval rate : ");
            SystemConfig.customerRetrievalRatePerSecond = Integer.parseInt(scanner.nextLine());

            System.out.print("Set maximum ticket capacity : ");
            SystemConfig.maxTicketCapacity = Integer.parseInt(scanner.nextLine());

            System.out.println("Set ");

        } catch (NumberFormatException e) {
            System.out.println("----------------------------------------");
            System.out.println(" Enter a valid Number");
            System.out.println("----------------------------------------\n");
        }
    }
}
