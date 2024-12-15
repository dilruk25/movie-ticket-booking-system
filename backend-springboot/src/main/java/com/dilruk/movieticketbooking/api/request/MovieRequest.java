package com.dilruk.movieticketbooking.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private Duration duration;
    @NotEmpty
    private String genre;
    @NotEmpty
    private double rating;
    @NotEmpty
    private Year year;

}
