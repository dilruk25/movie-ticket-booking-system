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
@Table(name = "customers")
public class Customer extends User {

    private String customerId;

   public Customer(String email, String password, String name, UserRole role, String customerId) {
        super(email, password, name, role);
        this.customerId = customerId;
    }
}
