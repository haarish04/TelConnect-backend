package com.example.TelConnect.controller;

import com.example.TelConnect.model.ServicePlan;
import com.example.TelConnect.service.ServicePlanService;

import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class ServicePlanController {

    private ServicePlanService servicePlanService;

    public ServicePlanController(ServicePlanService servicePlanService){
        this.servicePlanService=servicePlanService;
    }

    //Handler to get plan details using Id
    @GetMapping("/{planId}")
    public ResponseEntity<ServicePlan> getPlan(@PathVariable String planId){
        ServicePlan plan= servicePlanService.getPlan(planId);
        if(plan!=null)
            return ResponseEntity.ok(plan);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //Handler to get all the plans
    @GetMapping
    public ResponseEntity<List<ServicePlan>> getAllPlans(){
        List<ServicePlan> plans= servicePlanService.getAllPlans();
        if(plans.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.ok(plans);

    }

}
