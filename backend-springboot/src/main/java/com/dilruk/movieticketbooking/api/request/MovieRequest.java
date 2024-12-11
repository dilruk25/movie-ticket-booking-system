package com.dilruk.movieticketbooking.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private LocalTime duration;
    @NotEmpty
    private String genre;
    @NotEmpty
    private double rating;

}
