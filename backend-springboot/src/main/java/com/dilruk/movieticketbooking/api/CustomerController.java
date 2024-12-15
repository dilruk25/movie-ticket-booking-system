package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.services.user.CustomerService;
import com.dilruk.movieticketbooking.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle customer-related requests, including creating, updating, and deleting customers.
 */
@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController extends AbstractUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomerService customerService;

    public CustomerController(UserService userService, CustomerService customerService, UserMapper userMapper) {
        super(userService, userMapper);
        this.customerService = customerService;
    }

    /**
     * Get a list of all customers in the system.
     *
     * @return ResponseEntity containing a list of all customers
     */
    @GetMapping
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
    @GetMapping("/{customerId}")
    public ResponseEntity<UserDTO> getCustomerById(@PathVariable String customerId) {
        try {
            UserDTO user = customerService.getUserByUserId(customerId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logger.info("Customer not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
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