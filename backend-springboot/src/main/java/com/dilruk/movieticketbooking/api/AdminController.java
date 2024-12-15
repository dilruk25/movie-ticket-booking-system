package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.AdminService;
import com.dilruk.movieticketbooking.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles all requests related to the Admin functionalities
 * such as creating and managing users, system configurations, and starting threads.
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController extends AbstractUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AdminService adminService;

    public AdminController(UserService userService, AdminService adminService, UserMapper userMapper) {
        super(userService, userMapper);
        this.adminService = adminService;
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
}
