package com.dilruk.movieticketbooking.model.consumer;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.model.Ticket;
import com.dilruk.movieticketbooking.model.pool.TicketPool;
import com.dilruk.movieticketbooking.model.producer.Vendor;
import com.dilruk.movieticketbooking.util.LogUtil;
import com.dilruk.movieticketbooking.util.SimulationManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class Customer implements Runnable {

    private static volatile AtomicBoolean isCustomerFinished = new AtomicBoolean(false);
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = SystemConfig.getCustomerRetrievalRate();
    }


    @Override
    public void run() {
        while (SimulationManager.getIsRunning() && !isCustomerFinished.get()) {
            try {

                if (Vendor.getIsVendorFinished().get() && ticketPool.getAvailableTicketList().isEmpty() && SystemConfig.getTotalTickets() == Ticket.getTicketCount().get()) {
                    LogUtil.printLogLn("----------------------------------------");
                    LogUtil.printLogLn("All tickets has been sold.");
                    LogUtil.printLogLn("----------------------------------------\n");
                    isCustomerFinished.set(true);
                    LogUtil.close();
                    break;
                }

                if (!ticketPool.getAvailableTicketList().isEmpty()) {
                    ticketPool.buyTicket();
                }

                Thread.sleep(1000 / customerRetrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LogUtil.printLogLn("Customer threads have been manually interrupted.");
                break;
            }
        }
    }
}
