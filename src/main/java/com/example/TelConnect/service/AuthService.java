package com.example.TelConnect.service;

import com.example.TelConnect.DTO.LoginRequestDTO;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
import com.example.TelConnect.DTO.UserSessionInfo;
import com.example.TelConnect.model.Customer;
import com.example.TelConnect.repository.CustomerRepository;
import com.example.TelConnect.repository.RoleRepository;
import com.example.TelConnect.security.ActiveUserStore;
import com.example.TelConnect.security.BlacklistJwt;
import com.example.TelConnect.security.CustomCustomerDetailsService;
import com.example.TelConnect.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BlacklistJwt blacklistJwt;
    private final CustomCustomerDetailsService customerDetailsService;
    private final ActiveUserStore activeUserStore;


    public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, BlacklistJwt blacklistJwt, CustomCustomerDetailsService customerDetailsService, ActiveUserStore activeUserStore) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.blacklistJwt = blacklistJwt;
        this.customerDetailsService = customerDetailsService;
        this.activeUserStore = activeUserStore;
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getCustomerEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);
            String name = jwtTokenProvider.getUserName(token);
            UserDetails userDetails = customerDetailsService.loadUserByUsername(name);
            activeUserStore.addUser("user:" + userDetails.getUsername(),
                    new UserSessionInfo(userDetails.getUsername(), jwtTokenProvider.issuedAt(token), jwtTokenProvider.getExpiry(token)));

            return token;

        } catch (BadCredentialsException ex) {
            if (customerRepository.findByCustomerEmail(loginRequestDTO.getCustomerEmail()).getCustomerId() == 1L) {
                System.out.println("Invalid admin login attempt"+ LocalDate.now());
            }
            return "";
        }
    }


    public boolean register( @Valid RegisterCustomerDTO newCustomer){
        Customer customer= new Customer();
        customer.setCustomerName(newCustomer.getCustomerName());
        customer.setCustomerEmail(newCustomer.getCustomerEmail());
        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        customer.setCustomerDOB(newCustomer.getCustomerDOB());
        customer.setCustomerAddress(newCustomer.getCustomerAddress());
        customer.setAccountCreationDate(LocalDate.now());
        customer.setCustomerPhno(newCustomer.getCustomerPhno());
        customer.setRole(Set.of(roleRepository.findByRoleName("ROLE_USER")));

        Customer savedCustomer =customerRepository.save(customer);
        return savedCustomer.getCustomerId() != null;
    }

    public void logout(String token){
        blacklistJwt.blacklistToken(token);
        String name = jwtTokenProvider.getUserName(token);
        UserDetails userDetails = customerDetailsService.loadUserByUsername(name);
        activeUserStore.removeUser("user:" +userDetails.getUsername());

    }
}
