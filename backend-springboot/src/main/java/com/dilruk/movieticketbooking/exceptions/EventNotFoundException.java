package com.dilruk.movieticketbooking.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
