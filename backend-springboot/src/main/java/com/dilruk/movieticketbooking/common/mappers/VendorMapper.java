package com.dilruk.movieticketbooking.common.mappers;

import com.dilruk.movieticketbooking.user.api.request.VendorRequest;
import com.dilruk.movieticketbooking.user.api.response.VendorResponse;
import com.dilruk.movieticketbooking.user.dtos.VendorDTO;
import com.dilruk.movieticketbooking.user.models.Vendor;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public VendorDTO fromRequestToDto(VendorRequest request) {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(request.getName());
        vendorDTO.setEmail(request.getEmail());
        vendorDTO.setPassword(request.getPassword());
        vendorDTO.setRole(request.getRole());

        return vendorDTO;
    }

    public Vendor fromDtoToEntity(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorDTO.getVendorId());
        vendor.setName(vendorDTO.getName());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPassword(vendorDTO.getPassword());
        vendor.setRole(vendorDTO.getRole());

        return vendor;
    }

    public VendorDTO fromEntityToDto(Vendor vendor) {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorId(vendor.getVendorId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setEmail(vendor.getEmail());
        vendorDTO.setPassword(vendor.getPassword());
        vendorDTO.setRole(vendor.getRole());

        return vendorDTO;
    }

    public VendorResponse fromDtoToResponse(VendorDTO vendorDTO) {
        VendorResponse response = new VendorResponse();
        response.setName(vendorDTO.getName());
        response.setEmail(vendorDTO.getEmail());
        response.setRole(vendorDTO.getRole());

        return response;
    }
}