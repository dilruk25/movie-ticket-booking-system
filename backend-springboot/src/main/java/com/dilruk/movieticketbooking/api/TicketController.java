package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.AddTicketsRequest;
import com.dilruk.movieticketbooking.dtos.TicketDTO;
import com.dilruk.movieticketbooking.exceptions.MovieNotFoundException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.services.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final TicketService ticketService;

    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/add-tickets")
    public ResponseEntity<List<TicketDTO>> addTickets(@RequestBody AddTicketsRequest addTicketsRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String vendorId = user.getUserId();
        try {
            List<TicketDTO> tickets = ticketService.addTickets(addTicketsRequest, vendorId);
            return ResponseEntity.ok().body(tickets);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}