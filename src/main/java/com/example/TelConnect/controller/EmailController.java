package com.example.TelConnect.controller;

import com.example.TelConnect.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sendMail")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController( EmailService emailService) {
        this.emailService= emailService;
    }

    @PostMapping("/welcome")
    public ResponseEntity<String> welcomeMailSender(@RequestParam String recipient, String name){
        if(emailService.customEmailSender("welcome",0,recipient,name))
            return ResponseEntity.ok().body("Email sent");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }

    @PostMapping("/sendOTP")
    public ResponseEntity<String> OTPMailSender(@RequestParam String recipient, String name){
        int otp=emailService.generateOTP();
        if(emailService.customEmailSender("otp",otp,recipient,name))
            return ResponseEntity.ok().body("Email sent");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }

    @PostMapping("/thankYou")
    public ResponseEntity<String> thankYouSender(@RequestParam String recipient, String name){
        if(emailService.customEmailSender("thankyou",0,recipient,name))
            return ResponseEntity.ok().body("Email sent");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }

    @PostMapping("/serviceActivation")
    public ResponseEntity<String> activationSender(@RequestParam String recipient, String name){
        if(emailService.customEmailSender("serviceactivation",0,recipient,name))
            return ResponseEntity.ok().body("Email sent");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending mail");
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestParam String recipient, @RequestParam int otp) {
        if (emailService.verifyOTP(recipient, otp)) {
            return ResponseEntity.ok().body("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
}
