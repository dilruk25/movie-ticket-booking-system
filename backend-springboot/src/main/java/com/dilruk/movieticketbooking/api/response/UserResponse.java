package com.dilruk.movieticketbooking.api.response;

import com.dilruk.movieticketbooking.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String name;
    private String email;
    private UserRole role;
}
