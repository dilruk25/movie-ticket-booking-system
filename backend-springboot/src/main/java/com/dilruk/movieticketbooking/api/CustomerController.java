package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle customer-related requests, including creating, updating, and deleting customers.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomerService customerService;
    private final UserMapper userMapper;

    /**
     * Creates a new customer.
     *
     * @param user the customer details provided in the request body
     * @return ResponseEntity containing the created customer data
     */
    @PostMapping
    public ResponseEntity<UserDTO> createCustomer(@RequestBody UserRequest user) {
        try {
            UserDTO savedCustomer = customerService.createUser(userMapper.fromRequestToDto(user));
            return ResponseEntity.ok(savedCustomer);
        } catch (UserAlreadyExistsException e) {
            logger.info("Customer already exists: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error occurred while creating customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Updates an existing customer's details.
     *
     * @param customerId the ID of the customer to update
     * @param customer   the new customer details to update
     * @return ResponseEntity containing the updated customer data
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<UserDTO> updateCustomer(@PathVariable String customerId, @RequestBody UserRequest customer) {
        try {
            UserDTO updatedCustomer = customerService.updateUser(customerId, userMapper.fromRequestToDto(customer));
            return ResponseEntity.ok(updatedCustomer);
        } catch (UserNotFoundException e) {
            logger.info("Customer not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId the ID of the customer to delete
     * @return ResponseEntity indicating the result of the deletion attempt
     */
    @DeleteMapping
    public ResponseEntity<UserDTO> deleteCustomer(@RequestParam("customerId") String customerId) {
        try {
            customerService.deleteUser(customerId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            logger.info("Customer not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}