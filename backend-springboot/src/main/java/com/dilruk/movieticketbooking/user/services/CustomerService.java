package com.dilruk.movieticketbooking.user.services;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(String customerId);
    CustomerDTO updateCustomer(String customerId, CustomerDTO customerDTO);
    void deleteCustomer(String customerId);
}
