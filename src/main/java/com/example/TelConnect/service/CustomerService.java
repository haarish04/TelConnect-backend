package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.model.RegisterCustomer;
import com.example.TelConnect.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

    public void saveCustomer(RegisterCustomer newCustomer) {
        Customer customer= new Customer();
        customer.setCustomerName(newCustomer.getCustomerName());
        customer.setCustomerEmail(newCustomer.getCustomerEmail());
        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        customer.setCustomerDOB(newCustomer.getCustomerDOB());
        customer.setCustomerAddress(newCustomer.getCustomerAddress());
        customer.setAccountCreationDate(LocalDate.now());
        customer.setRole("USER");

        customerRepository.save(customer);
    }


    public Customer getByCustomerEmail(String email) {
        return customerRepository.findByCustomerEmail(email);
    }

    public int authenticateCustomer(String email, String password) {
        List<Customer> customers = customerRepository.findAll();
//        Customer customer = customers.stream()
//                .filter(c -> c.getCustomerEmail().equals(email))
//                .findFirst()
//                .orElse(null);
        Customer customer = customerRepository.findByCustomerEmail(email);
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

    public boolean deleteCustomer(String email){
        if(customerRepository.findByCustomerEmail(email)!= null) {
            customerRepository.
                    deleteById((customerRepository.findByCustomerEmail(email)).getCustomerId());
            return true;
        }
        else
            return false;
    }

    private Customer convertEntity(Customer customer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());
        return customer;
    }
}
