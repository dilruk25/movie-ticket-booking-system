package com.dilruk.movieticketbooking.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String role;
    @NotEmpty
    @Size(min = 6, max = 10)
    private String password;

}
