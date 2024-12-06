package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.CustomerMapper;
import com.dilruk.movieticketbooking.user.dtos.CustomerDTO;
import com.dilruk.movieticketbooking.user.models.Customer;
import com.dilruk.movieticketbooking.user.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> existCustomer = customerRepository.findCustomerByEmail(customerDTO.getEmail());
        if (existCustomer.isPresent()) {
            throw new DuplicateDataException("Customer email already exists");
        }

        Customer savedCustomer = customerRepository.save(customerMapper.fromDtoToEntity(customerDTO));
        return customerMapper.fromEntityToDto(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::fromEntityToDto).toList();
    }

    @Override
    public CustomerDTO getCustomerById(String customerId) {
        Customer existCustomer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + customerId));

        return customerMapper.fromEntityToDto(existCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(String customerId, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + customerId));

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPassword(customerDTO.getPassword());
        existingCustomer.setRole(customerDTO.getRole());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return customerMapper.fromEntityToDto(updatedCustomer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        Customer existCustomer = customerRepository.findCustomerByCustomerId(customerId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + customerId));

        customerRepository.delete(existCustomer);
    }
}
