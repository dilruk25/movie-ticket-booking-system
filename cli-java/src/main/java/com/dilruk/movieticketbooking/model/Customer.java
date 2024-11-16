package com.dilruk.movieticketbooking.model;

import com.dilruk.movieticketbooking.util.UserRoleEnum;

import java.util.UUID;

public class Customer extends Person {

    private final String customerId;
    private final Enum role;

    public Customer() {
        super();
        this.customerId = String.valueOf(UUID.randomUUID());
        this.role = UserRoleEnum.CUSTOMER;
    }

}
