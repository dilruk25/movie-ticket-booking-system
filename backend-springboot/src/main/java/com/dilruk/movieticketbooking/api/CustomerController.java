package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTO> createCustomer(@RequestBody UserRequest user) {
        try {
            UserDTO savedCustomer = customerService.createUser(userMapper.fromRequestToDto(user));
            return ResponseEntity.ok(savedCustomer);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<UserDTO> getCustomerById(@PathVariable String customerId) {
        try {
            UserDTO user = customerService.getUserByUserId(customerId);
            return ResponseEntity.ok(user);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<UserDTO> updateCustomer(@PathVariable String customerId, @RequestBody UserRequest customer) {
        try {
            UserDTO updatedCustomer = customerService.updateUser(customerId, userMapper.fromRequestToDto(customer));
            return ResponseEntity.ok(updatedCustomer);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteCustomer(@RequestParam("customerId") String customerId) {
        try {
            customerService.deleteUser(customerId);
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
