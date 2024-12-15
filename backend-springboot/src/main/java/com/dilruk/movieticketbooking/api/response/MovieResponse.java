package com.dilruk.movieticketbooking.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    private String movieId;
    private String title;
    private Duration duration;
    private String genre;
    private double rating;
    private Year year;
}
