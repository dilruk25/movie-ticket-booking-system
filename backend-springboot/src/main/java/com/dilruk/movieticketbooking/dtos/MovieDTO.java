package com.dilruk.movieticketbooking.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Year;

@Getter
@Setter
public class MovieDTO {

    private String movieId;
    private String title;
    private Duration duration;
    private String genre;
    private double rating;
    private Year year;

}
