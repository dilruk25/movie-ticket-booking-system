package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.VendorService;
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

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<UserDTO> vendors = userService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable String id) {
        try {
            VendorDTO vendor = userService.getVendorById(id);
            return ResponseEntity.ok(vendor);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable String id, @RequestBody VendorRequest vendor) {
        try {
            VendorDTO updatedVendor = userService.updateVendor(id, userMapper.fromRequestToDto(vendor));
            return ResponseEntity.ok(updatedVendor);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<VendorDTO> deleteVendor(@RequestParam("vendorId") String vendorId) {
        try {
            userService.deleteVendor(vendorId);
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
