package com.dilruk.movieticketbooking.exceptions;

public class MovieAlreadyExistsException extends RuntimeException {

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
