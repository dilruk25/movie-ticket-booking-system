package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.VendorMapper;
import com.dilruk.movieticketbooking.user.dtos.VendorDTO;
import com.dilruk.movieticketbooking.user.models.Vendor;
import com.dilruk.movieticketbooking.user.repositories.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;


    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Optional<Vendor> existVendor = vendorRepository.findVendorByEmail(vendorDTO.getEmail());
        if (existVendor.isPresent()) {
            throw new DuplicateDataException("Vendor email already exists");
        }

        Vendor savedVendor = vendorRepository.save(vendorMapper.fromDtoToEntity(vendorDTO));
        return vendorMapper.fromEntityToDto(savedVendor);
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.stream().map(vendorMapper::fromEntityToDto).toList();
    }

    @Override
    public VendorDTO getVendorById(String vendorId) {
        Vendor existVendor = vendorRepository.findVendorByVendorId(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + vendorId));

        return vendorMapper.fromEntityToDto(existVendor);
    }

    @Override
    public VendorDTO updateVendor(String vendorId, VendorDTO vendorDTO) {
        Vendor existingVendor = vendorRepository.findVendorByVendorId(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + vendorId));

        existingVendor.setName(vendorDTO.getName());
        existingVendor.setEmail(vendorDTO.getEmail());
        existingVendor.setPassword(vendorDTO.getPassword());
        existingVendor.setRole(vendorDTO.getRole());

        Vendor updatedVendor = vendorRepository.save(existingVendor);

        return vendorMapper.fromEntityToDto(updatedVendor);
    }

    @Override
    public void deleteVendor(String vendorId) {
        Vendor existVendor = vendorRepository.findVendorByVendorId(vendorId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + vendorId));

        vendorRepository.delete(existVendor);
    }
}
