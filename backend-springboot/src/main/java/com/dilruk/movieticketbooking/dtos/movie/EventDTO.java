package com.dilruk.movieticketbooking.dtos.movie;

import java.time.LocalDate;

public record EventDTO(String eventId, String title, LocalDate startDate, LocalDate endDate, int duration) {
}