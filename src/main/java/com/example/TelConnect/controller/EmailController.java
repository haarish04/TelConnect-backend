package com.example.TelConnect.controller;

import com.example.TelConnect.model.Customer;
import com.example.TelConnect.service.CustomerService;
import com.example.TelConnect.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name= "Email", description = "Email operations")
@RestController
@RequestMapping("/api/emails")
public class EmailController {
    private final EmailService emailService;
    private final CustomerService customerService;

    @Autowired
    public EmailController( EmailService emailService, CustomerService customerService) {
        this.emailService= emailService;
        this.customerService= customerService;
    }

    //Handler to push welcome email
    @PostMapping("/welcome")
    public ResponseEntity<String> welcomeMailSender(@RequestBody String recipient, @RequestBody String name) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", "welcome");
        variables.put("recipient", recipient);
        variables.put("name", name);

        return emailService.customEmailSender(variables)
                ? ResponseEntity.ok("Email sent")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }


    //Handler to push OTP mail
    @PostMapping("/OTP")
    public ResponseEntity<String> OTPMailSender(@RequestBody String recipient, @RequestBody String name) {
        int otp = emailService.generateOTP();

        Map<String, Object> variables = new HashMap<>();
        variables.put("type", "otp");
        variables.put("otp", otp);
        variables.put("recipient", recipient);
        variables.put("name", name);

        return emailService.customEmailSender(variables)
                ? ResponseEntity.ok("Email sent")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }


    //Handler to push thank-you mail
    @PostMapping("/thank-you")
    public ResponseEntity<String> thankYouSender(@RequestBody String recipient, @RequestBody String name, @RequestBody String plan) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", "thankyou");
        variables.put("recipient", recipient);
        variables.put("name", name);
        variables.put("plan", plan);

        return emailService.customEmailSender(variables)
                ? ResponseEntity.ok("Email sent")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }

    //Handler to push service activation mail
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/service-activation")
    public ResponseEntity<String> activationSender(@RequestBody String recipient, @RequestBody String name, @RequestBody String plan) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("type", "serviceactivation");
        variables.put("recipient", recipient);
        variables.put("name", name);
        variables.put("plan", plan);

        return emailService.customEmailSender(variables)
                ? ResponseEntity.ok("Email sent")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }


    //Handler to verify OTP submitted
    @PostMapping("/otp/verify")
    public ResponseEntity<String> verifyOTP(@RequestBody String recipient, @RequestBody int otp) {
        if (emailService.verifyOTP(recipient, otp)) {
            return ResponseEntity.ok().body("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

    @PostMapping("/support")
    public ResponseEntity<String> supportRequest(@RequestBody String message, HttpServletRequest request){
        String requesterEmail= request.getAttribute("userName").toString();
        Customer customer=customerService.getByCustomerEmail(requesterEmail);

        Map<String, Object> variables = new HashMap<>();
        variables.put("type", "support");
        variables.put("recipient", "telconnecta@gmail.com");
        variables.put("name", "Admin");
        variables.put("customerEmail", customer.getCustomerEmail());
        variables.put("customerName", customer.getCustomerName());
        variables.put("customerID", customer.getCustomerId());
        variables.put("customerPhone", customer.getCustomerPhno());
        variables.put("message", message);

        return emailService.customEmailSender(variables)
                ? ResponseEntity.ok("Email sent")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }
}
