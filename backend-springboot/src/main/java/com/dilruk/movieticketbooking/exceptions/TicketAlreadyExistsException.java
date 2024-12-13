package com.dilruk.movieticketbooking.exceptions;

public class TicketAlreadyExistsException extends RuntimeException {

    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
