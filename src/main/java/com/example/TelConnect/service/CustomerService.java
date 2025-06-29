package com.example.TelConnect.service;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
import com.example.TelConnect.DTO.UpdateRequestDTO;
import com.example.TelConnect.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    //Method to create new customer entry in database
//    public void saveCustomer(RegisterCustomerDTO newCustomer) {
//        Customer customer= new Customer();
//        customer.setCustomerName(newCustomer.getCustomerName());
//        customer.setCustomerEmail(newCustomer.getCustomerEmail());
//        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
//        customer.setCustomerDOB(newCustomer.getCustomerDOB());
//        customer.setCustomerAddress(newCustomer.getCustomerAddress());
//        customer.setAccountCreationDate(LocalDate.now());
//        customer.setCustomerPhno(newCustomer.getCustomerPhno());
//        customer.setRole("USER");
//
//        customerRepository.save(customer);
//    }


    //Utility method to find customer by email
    public Customer getByCustomerEmail(String email) {
        return customerRepository.findByCustomerEmail(email);
    }

    //Method to find customer by Id
    public Customer getByCustomerId(Long customerId){
        return customerRepository.findById(customerId).orElse(null);
    }

    //Method to authenticate the customer manually by checking email and password and matching with db entry
    public int authenticateCustomer(String email, String password) {
        Customer customer = customerRepository.findByCustomerEmail(email);
        if (customer != null) {
            if(passwordEncoder.matches(password, customer.getPassword()))
                return 1;
            else
                return 0;
        }
        return -1;
    }

    //Utility method to find all customers
    public List<Customer> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());
    }

    //Method to delete customer entry
    public boolean deleteCustomer(String email){
        if(customerRepository.findByCustomerEmail(email)!= null) {
            customerRepository.
                    deleteById((customerRepository.findByCustomerEmail(email)).getCustomerId());
            return true;
        }
        else
            return false;
    }

    //Method to update customer details limited to updating password and or address and or DOB
    public boolean updateCustomerDetails(UpdateRequestDTO updateCustomer){
        Customer existingCustomer=customerRepository.findByCustomerEmail(updateCustomer.getCustomerEmail());
        if(existingCustomer==null)
            return false;

        existingCustomer.setPassword(passwordEncoder.encode(updateCustomer.getPassword()));
        existingCustomer.setCustomerAddress(updateCustomer.getCustomerAddress());
        existingCustomer.setCustomerDOB(updateCustomer.getCustomerDOB());


        customerRepository.save(existingCustomer);
        return true;

    }

    //Utility class to stream customers based on name and email
    private Customer convertEntity(Customer customer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());
        return customer;
    }
}
