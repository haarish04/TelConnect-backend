package com.example.TelConnect.controller;

import com.example.TelConnect.model.CustomerPlanMapping;
import com.example.TelConnect.service.CustomerPlanService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("customer/plan")
public class CustomerPlanController {

    private final CustomerPlanService customerPlanService;

    public CustomerPlanController(CustomerPlanService customerPlanService){
        this.customerPlanService=customerPlanService;
    }

    //Handler to enroll new customer and map to a service
    @PostMapping("/enrollCustomer")
    public ResponseEntity<String> enrollCustomer(@RequestBody CustomerPlanMapping customerPlanMapping){
        if(customerPlanService.createNewCustomerPlanMapping(customerPlanMapping))
            return ResponseEntity.ok("Customer enrolled");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to enroll customer");
    }

    //Update status of the plan chosen by customer
    @PatchMapping("/updateStatus/{customerId}/{planId}/admin?={adminId}")
    public ResponseEntity<String> updateStatus(@PathVariable Long customerId, @PathVariable String planId, @RequestParam String status, @PathVariable Long adminId ){
        if(adminId==1L){
            if(customerPlanService.updateCustomerPlanStatus(customerId,planId,status))
                return ResponseEntity.ok("Status updated");
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    //Handler to get status of customer and their plan
    @GetMapping("/getStatus/{customerId}")
    public ResponseEntity<String> getCustomerStatus(@PathVariable Long customerId){
        String response= customerPlanService.getCustomerPlanStatus(customerId);
        if(response.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer has no existing plans");
        return ResponseEntity.ok(response);
    }


}
