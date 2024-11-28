package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
