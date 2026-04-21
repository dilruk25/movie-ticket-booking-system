package com.ticketcorner.movieticketbooking.api.v1.controller;

import com.ticketcorner.movieticketbooking.api.v1.ro.request.UserRequest;
import com.ticketcorner.movieticketbooking.dto.UserDTO;
import com.ticketcorner.movieticketbooking.common.mappers.UserMapper;
import com.ticketcorner.movieticketbooking.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Abstract base controller class for handling user API endpoints.
 * Provides common functionalities for user registration, login, and logout.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    protected final UserService userService;
    protected final UserMapper userMapper;

    /**
     * Creates a new user in the system. Throws an exception if the user already exists.
     *
     * @param request The user data transfer object containing the details for the new user.
     * @return The created user DTO.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRequest request) {
        UserDTO userDTO = userMapper.fromRequestToDto(request);
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }
}