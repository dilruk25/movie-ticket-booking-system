package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.user.dtos.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO createVendor(VendorDTO vendorDTO);
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(String vendorId);
    VendorDTO updateVendor(String vendorId, VendorDTO vendorDTO);
    void deleteVendor(String vendorId);
}
