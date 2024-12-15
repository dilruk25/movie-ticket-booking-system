package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.AddTicketsRequest;
import com.dilruk.movieticketbooking.dtos.TicketDTO;
import com.dilruk.movieticketbooking.exceptions.MovieNotFoundException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import com.dilruk.movieticketbooking.services.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling ticket-related requests, including adding tickets for a specific movie.
 */
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TicketService ticketService;
    private final TicketProcessor ticketProcessor;

    /**
     * Adds tickets for a movie. This operation is restricted to users with the "VENDOR" role.
     *
     * @param addTicketsRequest the request body containing details of the tickets to be added
     * @return ResponseEntity containing a list of the added tickets, or an error response
     */
    @PostMapping("/add-tickets")
    public ResponseEntity<List<TicketDTO>> addTickets(@RequestBody AddTicketsRequest addTicketsRequest) {

        try {
            // Add tickets using the ticket service
            List<TicketDTO> tickets = ticketService.addTickets(addTicketsRequest);
            return ResponseEntity.ok().body(tickets);
        } catch (UserNotFoundException | MovieNotFoundException e) {
            // Log the error and return a 404 Not Found response
            logger.error("Error occurred: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log unexpected errors and return a 500 Internal Server Error response
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Get the total ticket count managed by the system.
     *
     * @return the total number of tickets available
     */
    @GetMapping("/count")
    public int getTicketCount() {
        return ticketProcessor.getTicketCount();
    }

}