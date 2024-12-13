package com.dilruk.movieticketbooking.dtos;

import com.dilruk.movieticketbooking.enums.IdPrefix;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.utils.IdGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String name;
    private String userId;
    private String email;
    private String password;
    private UserRole role;

    public UserDTO() {

        if (this.role.equals(UserRole.ROLE_ADMIN)) {
            this.userId = IdGenerator.generateId(IdPrefix.ADMIN_PREFIX.getPrefix());
        } else if (this.role.equals(UserRole.ROLE_CUSTOMER)) {
            this.userId = IdGenerator.generateId(IdPrefix.CUSTOMER_PREFIX.getPrefix());
        } else if (this.role.equals(UserRole.ROLE_VENDOR)) {
            this.userId = IdGenerator.generateId(IdPrefix.VENDOR_PREFIX.getPrefix());
        }
    }
}
