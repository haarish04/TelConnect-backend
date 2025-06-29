package com.example.TelConnect.service;

import com.example.TelConnect.DTO.LoginRequestDTO;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
import com.example.TelConnect.repository.RoleRepository;
import com.example.TelConnect.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider JwtTokenProvider;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public String login(LoginRequestDTO loginRequestDTO){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getCustomerEmail(),
                loginRequestDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtTokenProvider.generateToken(authentication);
    }

    public void register(RegisterCustomerDTO newCustomer){
        Customer customer= new Customer();
        customer.setCustomerName(newCustomer.getCustomerName());
        customer.setCustomerEmail(newCustomer.getCustomerEmail());
        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        customer.setCustomerDOB(newCustomer.getCustomerDOB());
        customer.setCustomerAddress(newCustomer.getCustomerAddress());
        customer.setAccountCreationDate(LocalDate.now());
        customer.setCustomerPhno(newCustomer.getCustomerPhno());
        customer.getRole().add(roleRepository.findByRoleName("USER"));

        customerRepository.save(customer);
    }
}
