package com.dilruk.movieticketbooking.user.api.response;

import com.dilruk.movieticketbooking.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {

    private String customerId;
    private String name;
    private String email;
    private UserRole role;
}
