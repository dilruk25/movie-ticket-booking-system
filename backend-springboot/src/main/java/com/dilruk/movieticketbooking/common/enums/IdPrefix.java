package com.dilruk.movieticketbooking.common.enums;

public enum IdPrefix {

    // "TC" stands for TicketCorner
    MOVIE_PREFIX("TCM"),
    TICKET_PREFIX("TCT"),
    EVENT_PREFIX("TCE"),
    CUSTOMER_PREFIX("TCC"),
    VENDOR_PREFIX("TCV"),
    ADMIN_PREFIX("TCA");

    private final String prefix;

    IdPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
