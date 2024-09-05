package com.example.TelConnect.controller;

import com.example.TelConnect.model.ServicePlan;
import com.example.TelConnect.service.ServicePlanService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class ServicePlanController {

    private ServicePlanService servicePlanService;

    public ServicePlanController(ServicePlanService servicePlanService){
        this.servicePlanService=servicePlanService;
    }

    @GetMapping("/getPlan/{planId}")
    public ResponseEntity<ServicePlan> getPlan(@PathVariable String planId){
        ServicePlan plan= servicePlanService.getPlan(planId);
        if(plan!=null)
            return ResponseEntity.ok(plan);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getAllPlans")
    public ResponseEntity<List<ServicePlan>> getAllPlans(){
        List<ServicePlan> plans= servicePlanService.getAllPlans();
        if(plans.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.ok(plans);

    }

    @PostMapping("/createPlan/admin?={adminId}")
    public ResponseEntity<String> createPlan(@RequestBody ServicePlan plan,@PathVariable Long adminId){
        if(adminId==1L){
            if(servicePlanService.createPlan(plan))
                return ResponseEntity.ok("New plan created");
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("PlanId already exists");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized operation");
    }

    @DeleteMapping("/deletePlan/{planId}/admin@={adminId}")
    public ResponseEntity<String> deletePlan(@PathVariable String planId, @PathVariable Long adminId){
        if(adminId==1L){
            servicePlanService.deletePlan(planId);
            return ResponseEntity.ok("Plan Deleted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Operation");
    }
}
