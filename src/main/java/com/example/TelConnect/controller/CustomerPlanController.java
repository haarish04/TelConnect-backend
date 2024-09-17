package com.example.TelConnect.controller;

import com.example.TelConnect.model.CustomerPlanMapping;
import com.example.TelConnect.service.CustomerPlanService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers/plans")
public class CustomerPlanController {

    private final CustomerPlanService customerPlanService;

    public CustomerPlanController(CustomerPlanService customerPlanService){
        this.customerPlanService=customerPlanService;
    }

    //Handler to enroll new customer and map to a service
    @PostMapping
    public ResponseEntity<String> enrollCustomer(@RequestBody CustomerPlanMapping customerPlanMapping){
        if(customerPlanService.createNewCustomerPlanMapping(customerPlanMapping))
            return ResponseEntity.ok("Customer enrolled");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to enroll customer");
    }

//    //Update status of the plan chosen by customer
//    @PatchMapping("/{customerId}/plans/{planId}/status")
//    public ResponseEntity<String> updateStatus(@PathVariable Long customerId, @PathVariable String planId, @RequestParam String status, @RequestParam Long adminId ){
//        if(adminId==1L){
//            if(customerPlanService.updateCustomerPlanStatus(customerId,planId,status))
//                return ResponseEntity.ok("Status updated");
//            else
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//    }

    //Handler to get status of customer and their plan
    @GetMapping("/{customerId}/plans/status")
    public ResponseEntity<String> getCustomerStatus(@PathVariable Long customerId){
        String response= customerPlanService.getCustomerPlanStatus(customerId);
        if(response.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer has no existing plans");
        return ResponseEntity.ok(response);
    }

//    //Handler to get details of all customers and their chosen plans
//    @GetMapping
//    public ResponseEntity<?> getCustomerPlans(@RequestParam Long adminId){
//        if(adminId==1L)
//            return ResponseEntity.ok(customerPlanService.getAllCustomerPlans());
//        else
//            return (ResponseEntity<?>) ResponseEntity.badRequest();
//    }


}
