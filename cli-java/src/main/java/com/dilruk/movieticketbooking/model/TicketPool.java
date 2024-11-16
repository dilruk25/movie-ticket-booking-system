package com.dilruk.movieticketbooking.model;

import com.dilruk.movieticketbooking.config.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {

    private static int ticketPoolCount = 1;

    // The total no of tickets available in the system
    private int totalTickets;

    // Maximum capacity of the ticket pool
    private int maxTicketCapacity;

    // Vendors' ticket release count per day
    private static int ticketReleaseRatePerSecond;

    // Tickets are stored in an array
    private List<Ticket> ticketList = new ArrayList<>(maxTicketCapacity);

    public TicketPool() {
        this.totalTickets = SystemConfig.getTotalTickets();
        this.maxTicketCapacity = SystemConfig.getMaxTicketCapacity();
        createTicketPool(ticketReleaseRatePerSecond);
    }

    public void createTicketPool(int ticketReleaseRatePerSecond ) {
        TicketPool ticketPool = new TicketPool();
        System.out.println("Ticket Pool has created");

        for (int i = 0; i < SystemConfig.getTotalTickets(); i++) {
            Ticket ticket = new Ticket();
            System.out.println("Ticket: " + ticket.getMovieTitle() + "added");
            ticketList.add(ticket);
        }
        System.out.println(ticketList);
    }

}
