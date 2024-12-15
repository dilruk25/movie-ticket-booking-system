package com.dilruk.movieticketbooking.models.user;

import com.dilruk.movieticketbooking.models.ticket.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Used numerical Primary key for database

    @Column(nullable = false, unique = true)
    private String userId; // Used Unique ID to deal with external operations

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ADMIN, VENDOR, CUSTOMER

    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets;
}