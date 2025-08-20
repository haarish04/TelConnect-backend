package com.example.TelConnect.service;

import com.example.TelConnect.model.EmailContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailContentFactory {

    @Autowired
    private final EmailTemplateService templateService;

    public EmailContentFactory(EmailTemplateService templateService) {
        this.templateService = templateService;
    }

    public EmailContent createEmail(String type, Map<String, Object> variables) {
        String html = templateService.buildEmail(type.toLowerCase(), variables);
        String subject = getSubject(type, variables);

        EmailContent email = new EmailContent();
        email.setSubject(subject);
        email.setTextPart("Please view this email in HTML format.");
        email.setHtmlPart(html);

        return email;
    }

    private String getSubject(String type, Map<String, Object> vars) {
        switch (type.toLowerCase()) {
            case "welcome":
                return "Welcome to TelConnect! Your Connection Starts Here";
            case "otp":
                return vars.get("otp") + " is your 2FA OTP";
            case "thankyou":
                return "Thank You for Choosing TelConnect - Connecting You to What Matters!";
            case "serviceactivation":
                return "Service Activation Alert";
            case "support":
                return "Support Request";
            default:
                return "TelConnect Notification";
        }
    }
}
