package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
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

    private final VendorService vendorService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTO> createVendor(@RequestBody UserRequest user) {
        try {
            UserDTO savedVendor = vendorService.createUser(userMapper.fromRequestToDto(user));
            return ResponseEntity.ok(savedVendor);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllVendors() {
        List<UserDTO> vendors = vendorService.getAllUsers();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<UserDTO> getVendorByVendorId(@PathVariable String vendorId) {
        try {
            UserDTO vendor = vendorService.getUserByUserId(vendorId);
            return ResponseEntity.ok(vendor);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<UserDTO> updateVendor(@PathVariable String customerId, @RequestBody UserRequest vendor) {
        try {
            UserDTO updatedVendor = vendorService.updateUser(customerId, userMapper.fromRequestToDto(vendor));
            return ResponseEntity.ok(updatedVendor);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteVendor(@RequestParam("vendorId") String vendorId) {
        try {
            vendorService.deleteUser(vendorId);
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
