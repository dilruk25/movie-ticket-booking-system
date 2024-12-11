package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserRequest admin) {
        try {
            UserDTO savedAdmin = adminService.createUser(userMapper.fromRequestToDto(admin));
            return ResponseEntity.ok(savedAdmin);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<UserDTO> getAdminById(@PathVariable String adminId) {
        try {
            UserDTO admin = adminService.getUserByUserId(adminId);
            return ResponseEntity.ok(admin);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<UserDTO> updateAdmin(@PathVariable String adminId, @RequestBody UserRequest admin) {
        try {
            UserDTO updatedAdmin = adminService.updateUser(adminId, userMapper.fromRequestToDto(admin));
            return ResponseEntity.ok(updatedAdmin);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteAdmin(@RequestParam("adminId") String adminId) {
        try {
            adminService.deleteUser(adminId);
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
