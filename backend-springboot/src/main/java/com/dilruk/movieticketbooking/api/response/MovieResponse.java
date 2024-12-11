package com.dilruk.movieticketbooking.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    private String movieId;
    private String title;
    private LocalTime duration;
    private String genre;
    private double rating;
}
