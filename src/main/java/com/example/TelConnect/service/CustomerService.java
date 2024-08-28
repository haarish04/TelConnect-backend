package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Customer customer);

    String authenticateCustomer(String email, String password);
    Customer findByCustomerEmail(String email);

    List<Customer> findAllCustomers();
}
