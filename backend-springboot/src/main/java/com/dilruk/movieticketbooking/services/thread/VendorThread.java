package com.dilruk.movieticketbooking.services.thread;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.pools.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VendorThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(VendorThread.class);
    public static AtomicBoolean isRunning = new AtomicBoolean(false);
    public static AtomicInteger soldTicketCount = new AtomicInteger(0);
    private final TicketProcessor ticketProcessor;
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int maxTicketCapacity;


    public VendorThread(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
        this.ticketPool = ticketProcessor.getTicketPool();
        this.totalTickets = ticketProcessor.getTicketCount();
        this.ticketReleaseRate = systemConfig.getTicketReleaseRate();
        this.maxTicketCapacity = systemConfig.getMaxTicketCapacity();
    }

    @Override
    public void run() {

        ticketProcessor.addTickets();
        soldTicketCount.incrementAndGet();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}