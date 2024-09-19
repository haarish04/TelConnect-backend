package com.example.TelConnect.controller;

import com.example.TelConnect.model.Customer;
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


    //Handler to get one customer
    @GetMapping("/{customerEmail}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String customerEmail) {
        Customer customer=customerService.getByCustomerEmail(customerEmail);
        if(customer!=null)
            return ResponseEntity.ok(customer);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    //Handler to get one customer using customerId
    @GetMapping("/Id={customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId){
        Customer customer= customerService.getByCustomerId(customerId);
        return ResponseEntity.ok(customer);
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
}
