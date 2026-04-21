package com.ticketcorner.movieticketbooking.common.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String message) {
        super(message);
    }
}
