package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.ConfigRequest;
import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import com.dilruk.movieticketbooking.services.user.AdminService;
import com.dilruk.movieticketbooking.services.user.CustomerService;
import com.dilruk.movieticketbooking.services.user.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles all requests related to the Admin functionalities
 * such as creating and managing users, system configurations, and starting threads.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/super")
@RequiredArgsConstructor
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final UserMapper userMapper;
    private final TicketProcessor ticketProcessor;

    /**
     * Create a new admin user.
     *
     * @param admin the admin details from the request body
     * @return ResponseEntity containing the created admin user data
     */
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

    /**
     * Configure the system settings with given configurations.
     *
     * @param config the system configuration details
     * @return ResponseEntity indicating the result of the configuration attempt
     */
    @PostMapping("/config")
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

    /**
     * Fetch the details of an admin user by ID.
     *
     * @param adminId the ID of the admin user
     * @return ResponseEntity containing the admin details
     */
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

    /**
     * Update an admins' details.
     *
     * @param adminId the ID of the admin to be updated
     * @param admin   the new admin details
     * @return ResponseEntity containing the updated admin details
     */
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

    /**
     * Delete an admin user by their ID.
     *
     * @param adminId the ID of the admin to be deleted
     * @return ResponseEntity indicating the result of the deletion attempt
     */
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

    /**
     * Retrieve the current system configurations.
     *
     * @return ResponseEntity containing the current system configuration
     */
    @GetMapping("/configure")
    public ResponseEntity<SystemConfig> getConfigs() {
        try {
            SystemConfig currentConfig = adminService.getConfiguration();
            return ResponseEntity.ok(currentConfig);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching configurations: {}", ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Start the Vendor thread to manage ticket operations.
     *
     * @return ResponseEntity indicating the result of starting the Vendor thread
     */
    @PostMapping("/threads/vendor/exec")
    public ResponseEntity<String> startVendor() {
        try {
            if (VendorThread.isRunning.get()) {
                return ResponseEntity.badRequest().body("Vendor thread is already running.");
            }
            // Retrieve system configuration
            SystemConfig config = adminService.getConfiguration();
            // Start vendor thread with configuration
            new VendorThread(config, ticketProcessor).start();
            return ResponseEntity.ok("Vendor thread started successfully.");
        } catch (Exception e) {
            logger.error("Failed to start vendor thread: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error starting vendor thread: " + e.getMessage());
        }
    }

    /**
     * Start the Customer thread to manage ticket purchases.
     *
     * @return ResponseEntity indicating the result of starting the Customer thread
     */
    @PostMapping("/threads/customer/exec")
    public ResponseEntity<String> startCustomer() {
        try {
            if (CustomerThread.isRunning.get()) {
                return ResponseEntity.badRequest().body("Customer thread is already running.");
            }
            // Retrieve system configuration
            SystemConfig config = adminService.getConfiguration();
            // Start customer thread with configuration
            new CustomerThread(config, ticketProcessor).start();
            return ResponseEntity.ok("Customer thread started successfully.");
        } catch (Exception e) {
            logger.error("Failed to start customer thread: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error starting customer thread: " + e.getMessage());
        }
    }

    /**
     * Get the total ticket count managed by the system.
     *
     * @return the total number of tickets available
     */
    @GetMapping("/count")
    public int getTicketCount() {
        return ticketProcessor.getTicketCount();
    }

    /**
     * Get a list of all vendors in the system.
     *
     * @return ResponseEntity containing a list of all vendors
     */
    @GetMapping("/vendors")
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
    @GetMapping("/vendors/{vendorId}")
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
     * Get a list of all customers in the system.
     *
     * @return ResponseEntity containing a list of all customers
     */
    @GetMapping("/customers")
    public ResponseEntity<List<UserDTO>> getAllCustomers() {
        List<UserDTO> customers = customerService.getAllUsers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Get the details of a specific customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return ResponseEntity containing the customer details
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<UserDTO> getCustomerById(@PathVariable String customerId) {
        try {
            UserDTO user = customerService.getUserByUserId(customerId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logger.info("Customer not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
