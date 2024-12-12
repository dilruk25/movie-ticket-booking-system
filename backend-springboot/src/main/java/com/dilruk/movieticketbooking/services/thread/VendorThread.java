package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VendorThread extends Thread {

    public static AtomicBoolean isRunning = new AtomicBoolean(false);
    public static AtomicInteger soldTicketCount = new AtomicInteger(0);

    private static final Logger logger = LoggerFactory.getLogger(VendorThread.class);
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int maxTicketCapacity;


    public VendorThread(SystemConfig systemConfig, TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.totalTickets = ticketPool.getTicketCount();
        this.ticketReleaseRate = systemConfig.getTicketReleaseRate();
        this.maxTicketCapacity = systemConfig.getMaxTicketCapacity();
    }

    @Override
    public void run() {
        while(isRunning.get() || soldTicketCount.get() <= totalTickets ) { // Add 10 tickets for demonstration
            ticketPool.addTickets(ticket);
            soldTicketCount.incrementAndGet();
            try {
                Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}