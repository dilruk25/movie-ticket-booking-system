package com.dilruk.movieticketbooking.movie.dtos;

import com.dilruk.movieticketbooking.common.enums.IdPrefix;
import com.dilruk.movieticketbooking.common.utils.IdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class MovieDTO {

    private String movieId;
    private String title;
    private LocalTime duration;
    private String genre;
    private double rating;

    public MovieDTO() {
        this.movieId = IdGenerator.generateId(IdPrefix.MOVIE_PREFIX.getPrefix());
    }
}
