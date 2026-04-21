package com.ticketcorner.movieticketbooking.common.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }
}
