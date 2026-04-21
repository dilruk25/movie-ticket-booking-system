package com.ticketcorner.movieticketbooking.common.exceptions;

public class TicketAlreadyExistsException extends RuntimeException {

    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
