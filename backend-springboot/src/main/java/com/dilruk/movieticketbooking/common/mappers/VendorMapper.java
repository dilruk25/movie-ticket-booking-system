package com.dilruk.movieticketbooking.common.mappers;

import com.dilruk.movieticketbooking.user.dtos.VendorDTO;
import com.dilruk.movieticketbooking.user.models.Vendor;

public class VendorMapper {
    public VendorDTO toDTO(Vendor vendor) {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorId(vendor.getVendorId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setEmail(vendor.getEmail());
        vendorDTO.setPassword(vendor.getPassword());

        return vendorDTO;
    }

    public Vendor toEntity(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorDTO.getVendorId());
        vendor.setName(vendorDTO.getName());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPassword(vendorDTO.getPassword());

        return vendor;
    }
}