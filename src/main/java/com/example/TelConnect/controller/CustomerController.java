package com.example.TelConnect.controller;

import jakarta.validation.Valid;
import com.example.TelConnect.model.Customer;
import com.example.TelConnect.model.LoginRequest;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody LoginRequest loginRequest) {
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

//    @GetMapping("verifyEmail")
//    public ResponseEntity<String> verifyCustomerEmail(@RequestParam String customerEmail){
//    }


    // Handler method to handle customer registration after verification of email
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody Customer customer,
                                                   BindingResult result) {
        Customer existingCustomer = customerService.getByCustomerEmail(customer.getCustomerEmail());

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

    //Handler method to update customer details
//    @PostMapping("/update")
//    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
//
//        Customer existingCustomer = customerService.getByCustomerEmail(customer.getCustomerEmail());
//
//        if (existingCustomer == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Account does not exist with this email");
//        }
//
//    }

    // Handler method to get list of customers
    @GetMapping("/getAll/admin?={adminId}")
    public ResponseEntity<String> getCustomers(@PathVariable Long adminId) {
        if(adminId==1L){
            List<Customer> customers = customerService.findAllCustomers();
            return ResponseEntity.ok(customers.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Operation");

    }

    //Handler to get one customer
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<String> getCustomerById(@RequestBody String customerEmail) {
        Customer customer=customerService.getByCustomerEmail(customerEmail);
        if(customer!=null)
            return ResponseEntity.ok(customer.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist");

    }

    //Handler method to delete customer (Access only for admin)
    //Modify method to accept customer_ID of sender of this request and match to the admin, else block the delete request
    @DeleteMapping("/delete{email}/admin?={adminId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerEmail, @PathVariable Long adminId){
        if(adminId==1L){
            if(customerService.deleteCustomer(customerEmail))
                return ResponseEntity.ok("Customer Deleted");
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized operation");
    }


    // Handler method to handle logout
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//        return ResponseEntity.ok("Logged out successfully");
//    }
}
