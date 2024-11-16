package com.dilruk.movieticketbooking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

// Here I want to create objects independently just to simulate the process.
// Hence, no relationship with entities like vendor to ticket
public class Ticket {

    private static long ticketCount = 1;

    private final String ticketId;
    private final String movieTitle;
    private final String seatLetter;
    private final int seatNumber;
    private final LocalDate date;
    private final LocalTime time;
    private final int price;

    /**
     * Default constructor for Ticket.
     * Automatically generates random attributes for each ticket,
     * ensuring that every ticket instance is unique.
     * Due to the complexity when assigning seat coordinates in a simulation, leave them.
     */
    public Ticket() {
        Random random = new Random();
        this.ticketId = String.valueOf(UUID.randomUUID());
        this.movieTitle = "Movie" + ticketCount;
        this.seatLetter = null; // Leave assigning due to increasing complexity for simulation// Leave assigning due to increasing complexity for simulation
        this.seatNumber = -1; // Leave assigning due to increasing complexity for simulation
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.price = random.nextInt(800, 4500);
        ticketCount++;
    }

    // Getters and Setters are removed because they are unnecessary for simulation purposes.

    public static long getTicketCount() {
        return ticketCount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getSeatLetter() {
        return seatLetter;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }
}
