package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);
    Optional<Customer> findByEmail(String email);
}
