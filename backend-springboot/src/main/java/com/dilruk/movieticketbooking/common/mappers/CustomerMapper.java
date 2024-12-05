package com.dilruk.movieticketbooking.common.mappers;

import com.dilruk.movieticketbooking.user.dtos.CustomerDTO;
import com.dilruk.movieticketbooking.user.models.Customer;

public class CustomerMapper {
    public CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPassword(customer.getPassword());

        return customerDTO;
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());

        return customer;
    }
}