package com.ticketcorner.movieticketbooking.common.enums;

import lombok.Getter;

@Getter
public enum IdPrefixEnum {

    // "TC" stands for TicketCorner
    MOVIE_PREFIX("TCM"),
    TICKET_PREFIX("TCT"),
    CUSTOMER_PREFIX("TCC"),
    VENDOR_PREFIX("TCV"),
    ADMIN_PREFIX("TCA");

    private final String prefix;

    IdPrefixEnum(String prefix) {
        this.prefix = prefix;
    }

}
