package com.example.TelConnect.controller;

import com.example.TelConnect.DTO.UserWhitelistDTO;
import com.example.TelConnect.enums.Status;
import com.example.TelConnect.model.UserWhitelist;
import com.example.TelConnect.service.UserWhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userwhitelist")
public class UserWhitelistController {

    @Autowired
    private UserWhitelistService userWhitelistService;

    //Get all userwhitelist
    @GetMapping
    public ResponseEntity<List<UserWhitelist>> getUserWhitelist() {
        return ResponseEntity.ok(userWhitelistService.getUserWhitelist());
    }

    //Handler to create new userwhitelist
    @PostMapping
    public ResponseEntity<UserWhitelist> createuserWhitelist(@RequestBody UserWhitelist userWhitelist) {

        UserWhitelist newUserWhitelist=userWhitelistService.createuserWhitelist(userWhitelist);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserWhitelist);
    }

    //Handler for update userWhitelist
    @PutMapping("/{customer_id}")
    public ResponseEntity<UserWhitelist> updateUserWhitelist(@PathVariable Long customer_id, @RequestBody UserWhitelistDTO userWhitelistDTO) {

        UserWhitelist updated = userWhitelistService.updateUserWhitelist(customer_id, userWhitelistDTO);
        if(updated==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(updated);
    }

    //Handler to delete userWhitelist
    @DeleteMapping("/{customer_id}")
    public ResponseEntity<String> deleteUserWhitelist(@PathVariable Long customer_id) {
        userWhitelistService.deleteUserWhitelist(customer_id);
        return ResponseEntity.ok("userWhitelist deleted successfully");
    }

    //status update
    @PatchMapping("/{customer_id}/{status}")
    public ResponseEntity<UserWhitelist> updateStatus(@PathVariable("customer_id") Long customerId, @PathVariable("status") String status) {
        UserWhitelist updated = userWhitelistService.updateStatus(customerId, status);
        if (updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }
}
