package com.ticketcorner.movieticketbooking.common.exceptions;

public class MovieAlreadyExistsException extends RuntimeException {

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
