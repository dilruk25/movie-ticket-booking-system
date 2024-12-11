package com.dilruk.movieticketbooking.exceptions;

public class BadUserCredentialException extends RuntimeException {

    public BadUserCredentialException(String message) {
        super(message);
    }
}
