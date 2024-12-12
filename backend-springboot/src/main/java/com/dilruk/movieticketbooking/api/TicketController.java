package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketPool ticketPool = new TicketPool(20);

    @PostMapping("/vendor/start")
    public String startVendor() {
        new VendorThread(ticketPool).start();
        return "Vendor thread started.";
    }

    @PostMapping("/customer/start")
    public String startCustomer() {
        new CustomerThread(ticketPool).start();
        return "Customer thread started.";
    }

    @GetMapping("/count")
    public int getTicketCount() {
        return ticketPool.getTicketCount();
    }
}