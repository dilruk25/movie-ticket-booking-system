package com.dilruk.movieticketbooking.common.exceptions;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException(String message) {
        super(message);
    }
}
