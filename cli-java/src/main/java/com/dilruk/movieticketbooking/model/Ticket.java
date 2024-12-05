package com.dilruk.movieticketbooking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represent a ticket for a movie.
 * Simulates the process of issuing tickets and purchasing tickets without involving Vendors or Customers
 */
public class Ticket {

    /**
     * Use static variable to track the number of tickets created.
     * For thread safety, use {@link AtomicLong}, which allows the counter to
     * increment safely and correctly, even when multiple threads are creating tickets at the same time.*/
    private static final AtomicLong ticketCount = new AtomicLong(0);

    private final String ticketId;
    private final String movieTitle;
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
        // Increment the ticket count atomically
        ticketCount.incrementAndGet();

        Random random = new Random();

        this.ticketId = String.valueOf(UUID.randomUUID());
        this.movieTitle = "Test Movie";
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.price = random.nextInt(800, 4500);

    }

    // Get total created ticket count (Used AtomicLong for thread safety)
    public static AtomicLong getTicketCount() {
        return Ticket.ticketCount;
    }

    public String getTicketId() {
        return this.ticketId;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", price=" + price +
                '}';
    }
}
