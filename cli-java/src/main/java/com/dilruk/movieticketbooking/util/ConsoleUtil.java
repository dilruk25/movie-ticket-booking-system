package com.dilruk.movieticketbooking.util;

import java.util.Scanner;

public class ConsoleUtil {

    /**
     * Prompts the user for a positive integer value.
     *
     * @param scanner Input scanner.
     * @param label   Label to display message that describe the input.
     * @return A valid positive integer entered by the user.
     */
    public static int promptForPositiveInteger(Scanner scanner, String label) {
        int value = -1;

        while (value <= 0) {
            System.out.print(label);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    System.out.println("----------------------------------------");
                    System.out.println(" Please enter a positive integer value.");
                    System.out.println("----------------------------------------\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("----------------------------------------");
                System.out.println(" Invalid input.\n Please enter a positive integer value.");
                System.out.println("----------------------------------------\n");
            }
        }
        return value;
    }
}
