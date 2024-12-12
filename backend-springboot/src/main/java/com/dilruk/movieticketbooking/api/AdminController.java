package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.ConfigRequest;
import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import com.dilruk.movieticketbooking.services.user.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/super")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final TicketPool ticketPool;

    @PostMapping
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserRequest admin) {
        try {
            UserDTO savedAdmin = adminService.createUser(userMapper.fromRequestToDto(admin));
            logger.info("Created admin: {}", savedAdmin);
            return ResponseEntity.ok(savedAdmin);

        } catch (UserAlreadyExistsException e) {
            logger.info("Admin already exists: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error occurred while creating admin: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/configure")
    public ResponseEntity<String> configureSystem(@RequestBody @Valid ConfigRequest config) {
        try {
            adminService.configureSystem(config);
            return ResponseEntity.ok("System configured successfully with the following settings: " + config);
        } catch (IllegalArgumentException e) {
            logger.error("Bad Configuration request: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Configuration failed: " + e.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + ex.getMessage());
        }
    }


    @GetMapping("/{adminId}")
    public ResponseEntity<UserDTO> getAdminById(@PathVariable String adminId) {
        try {
            UserDTO admin = adminService.getUserByUserId(adminId);
            return ResponseEntity.ok(admin);

        } catch (UserNotFoundException e) {
            logger.info("Admin not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<UserDTO> updateAdmin(@PathVariable String adminId, @RequestBody UserRequest admin) {
        try {
            UserDTO updatedAdmin = adminService.updateUser(adminId, userMapper.fromRequestToDto(admin));
            logger.info("Updated admin: {}", updatedAdmin);
            return ResponseEntity.ok(updatedAdmin);
        } catch (UserNotFoundException e) {
            logger.info("Admin not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteAdmin(@RequestParam("adminId") String adminId) {
        try {
            adminService.deleteUser(adminId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            logger.info("Admin not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/threads/vendor/exec")
    public String startVendor() {
        new VendorThread(ticketPool).start();
        return "Vendor thread started.";
    }

    @PostMapping("/threads/customer/exec")
    public String startCustomer() {
        new CustomerThread(ticketPool).start();
        return "Customer thread started.";
    }

    @GetMapping("/count")
    public int getTicketCount() {
        return ticketPool.getTicketCount();
    }
}
