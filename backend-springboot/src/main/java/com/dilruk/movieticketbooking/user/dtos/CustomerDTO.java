package com.dilruk.movieticketbooking.user.dtos;

import com.dilruk.movieticketbooking.common.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private String customerId;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
