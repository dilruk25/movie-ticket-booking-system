package com.ticketcorner.movieticketbooking.api.v1.ro.request;

import com.ticketcorner.movieticketbooking.domain.ticket.entity.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String userId; // Used Unique ID to deal with external operations

    private String name;

    private String email;

    private String password;

    private String role;

    private List<Ticket> tickets;

}
