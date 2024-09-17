package com.example.TelConnect.controller;

import com.example.TelConnect.DTO.VerificationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.VerificationService;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService){
        this.verificationService=verificationService;
    }

    //Handler to save verification attempt
    @PostMapping
    public ResponseEntity<String> saveVerification(@RequestBody VerificationRequestDTO newVerificationRequest){
        verificationService.saveVerification(newVerificationRequest);
        return ResponseEntity.ok("New verification request created");
    }

    //Handler to get verification status of customer
    @GetMapping("/{customerId}/status")
    public ResponseEntity<String> getVerificationStatus(@PathVariable Long customerId){
        String response= verificationService.getVerificationStatus(customerId);
        if(response.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No verification request submitted by customer");
        else
            return ResponseEntity.ok(response);

    }

//    //Handler to update verification status
//    @PatchMapping("/{customerId}/status")
//    public ResponseEntity<String> updateVerificationStatus( @PathVariable Long customerId,@RequestParam String status){
//        verificationService.updateVerificationStatus(customerId, status);
//        return ResponseEntity.ok("Status updated successfully");
//    }

//    //Handler to get all the verification attempts
//    @GetMapping
//    public ResponseEntity<?> getAllVerificationAttempts(@RequestParam Long adminId){
//        if(adminId==1L)
//            return ResponseEntity.ok(verificationService.getAllVerificationAttempts());
//        else
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized operation");
//
//    }
}
