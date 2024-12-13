package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling vendor-related requests, including creating, updating, and deleting vendor profiles.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VendorService vendorService;
    private final UserMapper userMapper;

    /**
     * Creates a new vendor profile.
     *
     * @param user the request body containing the vendor details to be created
     * @return ResponseEntity containing the created vendor's data, or an error response
     */
    @PostMapping
    public ResponseEntity<UserDTO> createVendor(@RequestBody UserRequest user) {
        try {
            UserDTO savedVendor = vendorService.createUser(userMapper.fromRequestToDto(user));
            return ResponseEntity.ok(savedVendor);
        } catch (UserAlreadyExistsException e) {
            // Log the error and return a 400 Bad Request response
            logger.info("Vendor already exists: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Log unexpected errors and return a 500 Internal Server Error response
            logger.error("Unexpected error occurred while creating vendor: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Updates an existing vendor's profile.
     *
     * @param customerId the ID of the vendor to be updated
     * @param vendor the request body containing the updated vendor details
     * @return ResponseEntity containing the updated vendor's data, or an error response
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<UserDTO> updateVendor(@PathVariable String customerId, @RequestBody UserRequest vendor) {
        try {
            UserDTO updatedVendor = vendorService.updateUser(customerId, userMapper.fromRequestToDto(vendor));
            return ResponseEntity.ok(updatedVendor);
        } catch (UserNotFoundException e) {
            // Log the error and return a 404 Not Found response
            logger.info("Vendor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a vendor profile.
     *
     * @param vendorId the ID of the vendor to be deleted
     * @return ResponseEntity indicating the result of the deletion operation
     */
    @DeleteMapping
    public ResponseEntity<UserDTO> deleteVendor(@RequestParam("vendorId") String vendorId) {
        try {
            vendorService.deleteUser(vendorId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            // Log the error and return a 404 Not Found response
            logger.info("Vendor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log unexpected errors and return a 500 Internal Server Error response
            logger.error("Unexpected error occurred while deleting vendor: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}