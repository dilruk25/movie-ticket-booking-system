package com.dilruk.movieticketbooking.services.thread;


import com.dilruk.movieticketbooking.config.SystemConfig;
import org.springframework.stereotype.Service;

@Service
public class ThreadManagementService {

    private final TicketProcessor ticketProcessor;

    public ThreadManagementService(TicketProcessor ticketProcessor) {
        this.ticketProcessor = ticketProcessor;
    }

    // Start the Vendor thread which will add tickets to the pool
    public void startVendorThread(SystemConfig systemConfig) {
        VendorThread vendorThread = new VendorThread(systemConfig, ticketProcessor);
        vendorThread.start();
    }

    // Start the Customer thread which will remove tickets from the pool
    public void startCustomerThread(SystemConfig systemConfig) {
        CustomerThread customerThread = new CustomerThread(systemConfig, ticketProcessor);
        customerThread.start();
    }
}
