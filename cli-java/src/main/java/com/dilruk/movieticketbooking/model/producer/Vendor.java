package com.dilruk.movieticketbooking.model.producer;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.util.LogUtil;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class Vendor implements Runnable {

    public static volatile AtomicBoolean isVendorFinished = new AtomicBoolean(false);
    private final TicketPool ticketPool;
    private final int totalTickets;
    private final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.totalTickets = SystemConfig.getTotalTickets();
        this.ticketReleaseRate = SystemConfig.getTicketReleaseRate();
    }

    public static AtomicBoolean getIsVendorFinished() {
        return isVendorFinished;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() || isVendorFinished.get()) {

            try {
                if (Ticket.getTicketCount().intValue() >= totalTickets) {
                    LogUtil.log("----------------------------------------");
                    LogUtil.log(" Total ticket limit reached: " + this.totalTickets);
                    LogUtil.log(" Ticket adding cannot be proceed");
                    LogUtil.log("----------------------------------------\n");
                    isVendorFinished.set(true);
                    return;
                }

                ticketPool.addTicket();
                Thread.sleep(1000 / ticketReleaseRate);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LogUtil.log("\nVendor threads have been manually interrupted.");
                break;
            }
        }
    }
}
