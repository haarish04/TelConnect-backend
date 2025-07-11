package com.example.TelConnect.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.service.NotificationService;

@Tag(name= "Notifications", description = "Notify operations")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService= notificationService;
    }

    //Handler to create new notification entry when email pushed to customer
    @PostMapping("/{customerId}")
    public ResponseEntity<String> createNotification(@PathVariable Long customerId, @RequestParam String message){
        notificationService.createNotification(customerId, message);
        return ResponseEntity.ok("Notification pushed to customer");

    }

    //Handler to get notifications sent to a customer
    @GetMapping("/{customerId}")
    public ResponseEntity<String> getNotification(@PathVariable Long customerId){
        String notifications = notificationService.getCustomerNotifications(customerId).toString();
        if(notifications.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No notifications");
        else
            return ResponseEntity.ok(notifications);
    }

}
