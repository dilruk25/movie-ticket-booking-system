package com.dilruk.movieticketbooking.ticket.models;

import com.dilruk.movieticketbooking.movie.models.Movie;
import com.dilruk.movieticketbooking.user.models.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_pools")
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketPoolId;
    private int totalTickets;
    private int availableTickets;
}
