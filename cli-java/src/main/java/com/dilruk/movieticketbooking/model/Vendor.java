package com.dilruk.movieticketbooking.model;

import com.dilruk.movieticketbooking.util.UserRoleEnum;

import java.util.UUID;

public class Vendor extends Person {

    private String vendorId;
    private Enum role;

    public Vendor() {
        super();
        this.vendorId = String.valueOf(UUID.randomUUID());
        this.role = UserRoleEnum.VENDOR;
    }
}
