package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.models.user.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
