package com.dilruk.movieticketbooking.models.event;

import com.dilruk.movieticketbooking.models.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private User vendor; // Represent the vendor who created the event

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTime> timeSlots = new ArrayList<>();

    public Event(String eventId, LocalDate date, LocalTime startTime, LocalTime endTime, User vendor, List<ShowTime> timeSlots) {
        this.eventId = eventId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.vendor = vendor;
        this.timeSlots = timeSlots;
    }
}