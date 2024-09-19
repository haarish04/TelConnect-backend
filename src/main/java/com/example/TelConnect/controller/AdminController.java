package com.example.TelConnect.controller;

import com.example.TelConnect.model.*;
import com.example.TelConnect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CustomerService customerService;
    private final CustomerPlanService customerPlanService;
    private final ServicePlanService servicePlanService;
    private final VerificationService verificationService;

    @Autowired
    public AdminController(CustomerService customerService, CustomerPlanService customerPlanService,
                           ServicePlanService servicePlanService, VerificationService verificationService) {
        this.customerService = customerService;
        this.customerPlanService = customerPlanService;
        this.servicePlanService = servicePlanService;
        this.verificationService = verificationService;
    }

    // Handler to get all customers
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Modify method to delete customer
    @DeleteMapping("/customers/{customerEmail}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerEmail) {
        if (customerService.deleteCustomer(customerEmail)) {
            return ResponseEntity.ok("Customer Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    // Update status of the plan chosen by customer
    @PatchMapping("/{customerId}/plans/{planId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long customerId, @PathVariable String planId, @RequestParam String status) {
        if (customerPlanService.updateCustomerPlanStatus(customerId, planId, status)) {
            return ResponseEntity.ok("Status updated");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
        }
    }

    // Handler to get details of all customers and their chosen plans
    @GetMapping("/customers/plans")
    public ResponseEntity<?> getCustomerPlans() {
        return ResponseEntity.ok(customerPlanService.getAllCustomerPlans());
    }

    // Handler to create a new plan
    @PostMapping("/newPlan")
    public ResponseEntity<String> createPlan(@RequestBody ServicePlan plan) {
        if (servicePlanService.createPlan(plan)) {
            return ResponseEntity.ok("New plan created");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("PlanId already exists");
        }
    }

    // Handler to delete existing plans
    @DeleteMapping("/{planId}/delete")
    public ResponseEntity<String> deletePlan(@PathVariable String planId) {
        servicePlanService.deletePlan(planId);
        return ResponseEntity.ok("Plan Deleted");
    }

    // Handler to edit existing plans
    @PatchMapping("/{planId}/edit")
    public ResponseEntity<String> updatePlan(@PathVariable String planId, @RequestBody ServicePlan plan) {
        if (servicePlanService.updatePlan(plan, planId)) {
            return ResponseEntity.ok("Plan updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found");
        }
    }

    // Handler to get all the verification attempts
    @GetMapping("/verificationAttempts")
    public ResponseEntity<?> getAllVerificationAttempts() {
        return ResponseEntity.ok(verificationService.getAllVerificationAttempts());
    }

}

