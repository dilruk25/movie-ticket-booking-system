package com.dilruk.movieticketbooking.user.api;

import com.dilruk.movieticketbooking.common.exceptions.BadUserCredentialException;
import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.CustomerMapper;
import com.dilruk.movieticketbooking.user.api.request.CustomerRequest;
import com.dilruk.movieticketbooking.user.dtos.CustomerDTO;
import com.dilruk.movieticketbooking.user.dtos.UserDTO;
import com.dilruk.movieticketbooking.user.services.CustomerServiceImpl;
import com.dilruk.movieticketbooking.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String email, @RequestParam String password) {
        try {
            AtomicBoolean isLoginSuccess = userService.login(email, password);
            return ResponseEntity.ok().build();
        } catch (BadUserCredentialException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
