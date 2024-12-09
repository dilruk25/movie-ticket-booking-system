package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.Vendor;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Registered
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findVendorByVendorId(String vendorId);

    Optional<Vendor> findVendorByName(String name);

    Optional<Vendor> findVendorByEmail(String email);
}
