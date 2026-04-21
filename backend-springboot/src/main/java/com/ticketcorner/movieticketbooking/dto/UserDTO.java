package com.ticketcorner.movieticketbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String name;
    private String userId;
    private String email;
    @JsonIgnore
    private String password;
    private String role;
}
