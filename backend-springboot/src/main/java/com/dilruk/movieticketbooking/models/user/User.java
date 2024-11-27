package com.dilruk.movieticketbooking.models.user;

import com.dilruk.movieticketbooking.enums.UserRole;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class User {

    private String name;
    private String email;
    private String password;
    private UserRole role;
}
