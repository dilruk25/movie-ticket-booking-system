package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.ConfigRequest;
import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.ThreadManagementService;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import com.dilruk.movieticketbooking.services.user.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;
    private final ThreadManagementService threadManagementService;

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
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
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
        } catch (Exception e) {
            logger.error("An error occurred while fetching configurations: {}", e.getMessage());
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
            threadManagementService.startVendorThread(config);
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
            threadManagementService.startCustomerThread(config);

            return ResponseEntity.ok("Customer thread started successfully.");

        } catch (Exception e) {
            logger.error("Failed to start customer thread: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error starting customer thread: " + e.getMessage());
        }
    }

    @PostMapping("/threads/vendor/stop")
    public ResponseEntity<String> stopVendor() {
        try {
            threadManagementService.stopVendorThread();
            return ResponseEntity.ok("Vendor thread stopped successfully.");
        } catch (Exception e) {
            logger.error("Failed to stop vendor thread: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error stopping vendor thread: " + e.getMessage());
        }
    }

    @PostMapping("/threads/customer/stop")
    public ResponseEntity<String> stopCustomer() {
        try {
            threadManagementService.stopCustomerThread();
            return ResponseEntity.ok("Customer thread stopped successfully.");
        } catch (Exception e) {
            logger.error("Failed to stop customer thread: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error stopping customer thread: " + e.getMessage());
        }
    }
}
