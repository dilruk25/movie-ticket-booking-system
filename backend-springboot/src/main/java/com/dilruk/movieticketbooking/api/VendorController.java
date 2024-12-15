package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.UserService;
import com.dilruk.movieticketbooking.services.user.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling vendor-related requests, including creating, updating, and deleting vendor profiles.
 */
@Slf4j
@RestController
@RequestMapping("/vendors")
public class VendorController extends AbstractUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VendorService vendorService;

    public VendorController(UserService userService, VendorService vendorService, UserMapper userMapper) {
        super(userService, userMapper);
        this.vendorService = vendorService;
    }

    /**
     * Get a list of all vendors in the system.
     *
     * @return ResponseEntity containing a list of all vendors
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllVendors() {
        List<UserDTO> vendors = vendorService.getAllUsers();
        return ResponseEntity.ok(vendors);
    }

    /**
     * Get the details of a specific vendor by their ID.
     *
     * @param vendorId the ID of the vendor to retrieve
     * @return ResponseEntity containing the vendor details
     */
    @GetMapping("/{vendorId}")
    public ResponseEntity<UserDTO> getVendorByVendorId(@PathVariable String vendorId) {
        try {
            UserDTO vendor = vendorService.getUserByUserId(vendorId);
            return ResponseEntity.ok(vendor);
        } catch (UserNotFoundException e) {
            logger.info("Vendor not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing vendor's profile.
     *
     * @param vendorId the ID of the vendor to be updated
     * @param vendor   the request body containing the updated vendor details
     * @return ResponseEntity containing the updated vendor's data, or an error response
     */
    @PutMapping("/{vendorId}")
    public ResponseEntity<UserDTO> updateVendor(@PathVariable String vendorId, @RequestBody UserRequest vendor) {
        try {
            UserDTO updatedVendor = vendorService.updateUser(vendorId, userMapper.fromRequestToDto(vendor));
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