package com.example.TelConnect.security;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomCustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomCustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCustomerEmail(email);

        if (customer != null) {
            return new org.springframework.security.core.userdetails.User(
                    customer.getCustomerEmail(),
                    customer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(customer.getRole()))
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

}
