package com.dilruk.movieticketbooking.dtos;

import com.dilruk.movieticketbooking.enums.IdPrefix;
import com.dilruk.movieticketbooking.utils.IdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.Year;

@Getter
@Setter
public class MovieDTO {

    private String movieId;
    private String title;
    private LocalTime duration;
    private String genre;
    private double rating;
    private Year year;

    public MovieDTO() {
        this.movieId = IdGenerator.generateId(IdPrefix.MOVIE_PREFIX.getPrefix());
    }
}
