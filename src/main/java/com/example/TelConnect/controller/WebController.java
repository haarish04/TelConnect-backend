package com.example.TelConnect.controller;

import jakarta.validation.Valid;
import com.example.TelConnect.model.Customer;
import com.example.TelConnect.model.LoginRequest;
import com.example.TelConnect.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class WebController {

    private final CustomerService customerService;

    @Autowired
    public WebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getCustomerEmail();
        String password = loginRequest.getPassword();
        try {
            // Authenticate the customer using your custom service
            String authenticate = customerService.authenticateCustomer(email, password);
            if (authenticate.equals("Login Success")) {
                // If authentication is successful
                return ResponseEntity.ok("Login successful");
            } else if (authenticate.equals("Login failed")) {
                // If authentication fails
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            } else if (authenticate.equals("User not found")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
            }
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Default return in case none of the conditions are met
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during login");
    }



    // Handler method to handle customer registration
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody Customer customer,
                                                   BindingResult result) {
        Customer existingCustomer = customerService.findByCustomerEmail(customer.getCustomerEmail());

        if (existingCustomer != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid registration data");
        }

        customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    // Handler method to get list of customers
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Handler method to handle logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }
}
