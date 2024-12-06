package com.dilruk.movieticketbooking.user.api;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.CustomerMapper;
import com.dilruk.movieticketbooking.user.api.request.CustomerRequest;
import com.dilruk.movieticketbooking.user.dtos.CustomerDTO;
import com.dilruk.movieticketbooking.user.services.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;


    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerRequest customer) {
        try {
            CustomerDTO savedCustomer = customerService.createCustomer(customerMapper.fromRequestToDto(customer));
            return ResponseEntity.ok(savedCustomer);
        } catch (DuplicateDataException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String id) {
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest customer) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerMapper.fromRequestToDto(customer));
            return ResponseEntity.ok(updatedCustomer);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<CustomerDTO> deleteCustomer(@RequestParam("customerId") String customerId) {
        try {
            customerService.deleteCustomer(customerId);
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
