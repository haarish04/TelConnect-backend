package com.example.TelConnect.controller;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
import com.example.TelConnect.DTO.LoginRequestDTO;
import com.example.TelConnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.CustomerService;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(CustomerService customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil=jwtUtil;
    }

    // Handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getCustomerEmail();
        String password = loginRequest.getPassword();
        try {
            // Authenticate the customer using your custom service
            int authenticate = customerService.authenticateCustomer(email, password);

            if (authenticate == 1) {
                Customer customer = customerService.getByCustomerEmail(email);
                if (customer.getCustomerId() == 1) {
                    // Generate JWT token for the admin
                    String token = jwtUtil.generateToken(email);
                    return ResponseEntity.ok(Collections.singletonMap("token", token));
                } else {
                    return ResponseEntity.ok("Login successful, but no admin privileges.");
                }
            } else if (authenticate == 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            } else if (authenticate == -1) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during login");
    }


    // Handler method to handle customer registration after verification of email
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterCustomerDTO newCustomer) {
        Customer existingCustomer = customerService.getByCustomerEmail(newCustomer.getCustomerEmail());

        if (existingCustomer != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("There is already an account registered with the same email");
        }

        customerService.saveCustomer(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

}
