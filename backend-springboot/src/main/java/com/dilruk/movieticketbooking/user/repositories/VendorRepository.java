package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
