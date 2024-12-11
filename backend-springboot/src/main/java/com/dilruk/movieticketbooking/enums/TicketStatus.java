package com.dilruk.movieticketbooking.enums;

public enum TicketStatus {
    AVAILABLE, SOLD, FREEZE; // Freeze status allows someone who failed the ticket transaction
                             // via online to keep there booking secure.
}