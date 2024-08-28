package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    //private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository /*, PasswordEncoder passwordEncoder*/) {
        this.customerRepository = customerRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());

        customer.setPassword(customer.getPassword());

        customer.setRole("User");
        customerRepository.save(customer);
    }


    @Override
    public Customer findByCustomerEmail(String email) {
        return customerRepository.findByCustomerEmail(email);
    }

    public String authenticateCustomer(String email, String password) {
        Customer customer = customerRepository.findByCustomerEmail(email);
        if (customer != null) {
            if(password.equals(customer.getPassword()))
                return "Login Success";

            else
                return "Login failed";
        }

        return "User not found";
    }

    @Override
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
