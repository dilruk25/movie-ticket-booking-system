package com.dilruk.movieticketbooking.user.dtos;

import com.dilruk.movieticketbooking.common.enums.IdPrefix;
import com.dilruk.movieticketbooking.common.enums.UserRole;
import com.dilruk.movieticketbooking.common.utils.IdGenerator;
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

        if (this.role.equals(UserRole.ADMIN)) {
            this.userId = IdGenerator.generateId(IdPrefix.ADMIN_PREFIX.getPrefix());
        } else if (this.role.equals(UserRole.CUSTOMER)) {
            this.userId = IdGenerator.generateId(IdPrefix.CUSTOMER_PREFIX.getPrefix());
        } else if (this.role.equals(UserRole.VENDOR)) {
            this.userId = IdGenerator.generateId(IdPrefix.VENDOR_PREFIX.getPrefix());
        }
    }
}
