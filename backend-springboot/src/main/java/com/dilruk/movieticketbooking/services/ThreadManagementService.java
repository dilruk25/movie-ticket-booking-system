package com.dilruk.movieticketbooking.services;


import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import org.springframework.stereotype.Service;

@Service
public class ThreadManagementService {

    private final TicketPool ticketPool;

    public ThreadManagementService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Start the Vendor thread which will add tickets to the pool
    public void startVendorThread(SystemConfig systemConfig) {
        VendorThread vendorThread = new VendorThread(systemConfig, ticketPool);
        vendorThread.start();
    }

    // Start the Customer thread which will remove tickets from the pool
    public void startCustomerThread(SystemConfig systemConfig) {
        CustomerThread customerThread = new CustomerThread(systemConfig, ticketPool);
        customerThread.start();
    }
}
