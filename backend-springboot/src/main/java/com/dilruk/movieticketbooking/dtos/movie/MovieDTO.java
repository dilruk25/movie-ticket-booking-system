package com.dilruk.movieticketbooking.dtos.movie;

import java.time.LocalTime;

public record MovieDTO(String movieId, String title, LocalTime duration, String genre, Double rating) {
}