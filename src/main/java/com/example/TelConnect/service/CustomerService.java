package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository , PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveCustomer(Customer customer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customer.setRole("User");
        customerRepository.save(customer);
    }


    public Customer getByCustomerEmail(String email) {
        return customerRepository.findByCustomerEmail(email);
    }

    public int authenticateCustomer(String email, String password) {
        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.stream()
                .filter(c -> c.getCustomerEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (customer != null) {
            if(passwordEncoder.matches(password, customer.getPassword()))
                return 1;

            else
                return 0;
        }

        return -1;
    }

    public List<Customer> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());
    }

    private Customer convertEntity(Customer customer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());
        // Note: Set other fields if needed
        return customer;
    }
}
