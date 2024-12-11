package com.dilruk.movieticketbooking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Multiplex { // Multiplex is the cinema building that has more theatres

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String multiplexID;

    private String name;

    private String location;
}
