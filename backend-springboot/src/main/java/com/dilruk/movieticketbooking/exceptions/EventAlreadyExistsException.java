package com.dilruk.movieticketbooking.exceptions;

public class EventAlreadyExistsException extends RuntimeException {

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
