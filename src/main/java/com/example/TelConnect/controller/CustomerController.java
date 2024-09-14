package com.example.TelConnect.controller;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.DTO.RegisterCustomerDTO;
import com.example.TelConnect.DTO.LoginRequestDTO;
import com.example.TelConnect.DTO.UpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getCustomerEmail();
        String password = loginRequest.getPassword();
        try {
            // Authenticate the customer using your custom service
            int authenticate = customerService.authenticateCustomer(email, password);

            // If authentication is successful
            if (authenticate == 1) {
                return ResponseEntity.ok("Login successful");

                // If authentication fails
            } else if (authenticate == 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");

                //Error in login
            } else if (authenticate ==-1) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Default return in case none of the conditions are met
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

    // Handler method to get list of customers
    @GetMapping
    public ResponseEntity<String> getCustomers(@RequestParam Long adminId) {
        if(adminId==1L){
            List<Customer> customers = customerService.findAllCustomers();
            return ResponseEntity.ok(customers.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Operation");

    }

    //Handler to get one customer
    @GetMapping("/{customerEmail}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerEmail) {
        Customer customer=customerService.getByCustomerEmail(customerEmail);
        if(customer!=null)
            return ResponseEntity.ok(customer);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    //Handler to update customer details
    @PatchMapping("/{customerEmail}")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerEmail,@RequestBody UpdateRequestDTO updateCustomer){
        updateCustomer.setCustomerEmail(customerEmail);
        boolean update =customerService.updateCustomerDetails(updateCustomer);

        if (update)
            return ResponseEntity.ok("Update Success");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Account does not exist with this email");

    }

    //Handler method to delete customer (Access only for admin)
    //Modify method to accept customer_ID of sender of this request and match to the admin, else block the delete request
    @DeleteMapping("/{customerEmail}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerEmail, @RequestParam Long adminId){
        if(adminId==1L){
            if(customerService.deleteCustomer(customerEmail))
                return ResponseEntity.ok("Customer Deleted");
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized operation");
    }

}
