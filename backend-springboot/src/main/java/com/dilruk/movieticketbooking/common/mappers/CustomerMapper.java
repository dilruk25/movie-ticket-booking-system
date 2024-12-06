package com.dilruk.movieticketbooking.common.mappers;

import com.dilruk.movieticketbooking.user.api.request.CustomerRequest;
import com.dilruk.movieticketbooking.user.api.response.CustomerResponse;
import com.dilruk.movieticketbooking.user.dtos.CustomerDTO;
import com.dilruk.movieticketbooking.user.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO fromRequestToDto(CustomerRequest request) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(request.getName());
        customerDTO.setEmail(request.getEmail());
        customerDTO.setPassword(request.getPassword());
        customerDTO.setRole(request.getRole());

        return customerDTO;
    }

    public Customer fromDtoToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());
        customer.setRole(customerDTO.getRole());

        return customer;
    }

    public CustomerDTO fromEntityToDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setRole(customer.getRole());

        return customerDTO;
    }

    public CustomerResponse fromDtoToResponse(CustomerDTO customerDTO) {
        CustomerResponse response = new CustomerResponse();
        response.setName(customerDTO.getName());
        response.setEmail(customerDTO.getEmail());
        response.setRole(customerDTO.getRole());

        return response;
    }
}