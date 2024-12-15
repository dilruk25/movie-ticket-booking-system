package com.dilruk.movieticketbooking.models.ticket;

import com.dilruk.movieticketbooking.models.movie.Movie;
import com.dilruk.movieticketbooking.models.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket { // Place the booking for the event

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String ticketId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    public Ticket(String ticketId, double price) {
        this.ticketId = ticketId;
        this.price = price;
    }

}
