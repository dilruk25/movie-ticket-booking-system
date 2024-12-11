package com.dilruk.movieticketbooking.user.api;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.user.services.VendorServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorServiceImpl vendorService;
    private final VendorMapper vendorMapper;


    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorRequest vendor) {
        try {
            VendorDTO savedVendor = vendorService.createVendor(vendorMapper.fromRequestToDto(vendor));
            return ResponseEntity.ok(savedVendor);
        } catch (DuplicateDataException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable String id) {
        try {
            VendorDTO vendor = vendorService.getVendorById(id);
            return ResponseEntity.ok(vendor);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable String id, @RequestBody VendorRequest vendor) {
        try {
            VendorDTO updatedVendor = vendorService.updateVendor(id, vendorMapper.fromRequestToDto(vendor));
            return ResponseEntity.ok(updatedVendor);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<VendorDTO> deleteVendor(@RequestParam("vendorId") String vendorId) {
        try {
            vendorService.deleteVendor(vendorId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
