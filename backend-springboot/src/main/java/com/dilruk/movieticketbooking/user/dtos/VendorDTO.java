package com.dilruk.movieticketbooking.user.dtos;

import com.dilruk.movieticketbooking.common.enums.IdPrefix;
import com.dilruk.movieticketbooking.common.enums.UserRole;
import com.dilruk.movieticketbooking.common.utils.IdGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorDTO {

    public VendorDTO() {
        this.vendorId = IdGenerator.generateId(IdPrefix.VENDOR_PREFIX.getPrefix());
    }

    private String vendorId;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
