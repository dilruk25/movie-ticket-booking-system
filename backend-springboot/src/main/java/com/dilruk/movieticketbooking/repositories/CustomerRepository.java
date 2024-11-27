package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
