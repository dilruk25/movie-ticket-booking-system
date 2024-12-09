package com.dilruk.movieticketbooking.user.models;

import com.dilruk.movieticketbooking.common.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vendors")
public class Vendor extends User {

    private String vendorId;

   public Vendor(String email, String password, String name, UserRole role, String vendorId) {
        super(email, password, name, role);
        this.vendorId = vendorId;
    }
}
