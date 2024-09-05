package com.example.TelConnect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.VerificationService;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService){
        this.verificationService=verificationService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveNotification(@RequestParam Long documentId, @RequestParam Long customerId){
        verificationService.saveVerification(documentId,customerId);
        return ResponseEntity.ok("New verification request created");
    }

    @GetMapping("/getStatus/{customerId}")
    public ResponseEntity<String> getVerificationStatus(@PathVariable Long customerId){
        String response= verificationService.getVerificationStatus(customerId);
        if(response.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No verification request submitted by customer");
        else
            return ResponseEntity.ok(response);

    }

    @PatchMapping("/updateStatus/{customerId}/{documentType}/status={status}")
    public ResponseEntity<String> updateVerificationStatus(@PathVariable Long customerId, @PathVariable String documentType, @PathVariable String status){
        verificationService.updateVerificationStatus(customerId, documentType, status);
        return ResponseEntity.ok("Status updated successfully");
    }
}
