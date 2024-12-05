package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByVendorId(String vendorId);
    Optional<Vendor> findByName(String name);
    Optional<Vendor> findByEmail(String email);
}
