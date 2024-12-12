package com.dilruk.movieticketbooking.models.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "show_times")
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String showTimeId;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event; // Represent the event instance that belongs current showtime instance

    public ShowTime(String showTimeId, Event event, LocalTime startTime, LocalTime endTime) {
        this.showTimeId = showTimeId;
        this.event = event;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}