package com.ticketcorner.movieticketbooking.common.exceptions;

public class BadUserCredentialException extends RuntimeException {

    public BadUserCredentialException(String message) {
        super(message);
    }
}
