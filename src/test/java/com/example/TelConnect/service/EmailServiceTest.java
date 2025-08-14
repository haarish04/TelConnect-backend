package com.example.TelConnect.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.TelConnect.DTO.SecretsCache;
import com.example.TelConnect.model.EmailContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;


class EmailServiceTest {

    private EmailService emailService;

    @InjectMocks
    SecretsCache secretsCache;

    @InjectMocks
    private EmailContentFactory emailContentFactory;

    @InjectMocks
    private EmailTemplateService emailTemplateService;

    @InjectMocks
    private TemplateEngine templateEngine;


    @BeforeEach
    public void setUp() {
        templateEngine= new TemplateEngine();
        emailTemplateService= new EmailTemplateService(templateEngine);
        emailContentFactory= new EmailContentFactory(emailTemplateService );
        emailService = new EmailService(emailContentFactory,secretsCache);
    }

    //Test the email method for packaging welcome message
    @Test
    public void testWelcomeMessage() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "testName");

        EmailContent emailContent = emailContentFactory.createEmail("welcome",variables);

        assertEquals("Welcome to TelConnect! Your Connection Starts Here", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
        assertNotNull(emailContent.getTextPart());
    }

    //Test the email method for packaging OTP message
    @Test
    public void testOTPMessage() {
        int otp = 123456;

        Map<String, Object> variables = new HashMap<>();
        variables.put("otp", otp);
        EmailContent emailContent = emailContentFactory.createEmail("otp",variables);

        System.out.println(emailContent.getHtmlPart());
        assertEquals("123456 is your 2FA OTP", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
    }

    //Test the email method for packaging thank you message
    @Test
    public void testThankYouMessage() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "testName");

        EmailContent emailContent = emailContentFactory.createEmail("thankyou",variables);

        assertEquals("Thank You for Choosing TelConnect - Connecting You to What Matters!", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
    }

    //Test the email method for packaging activation message
    @Test
    public void testServiceActivationMessage() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "testName");

        EmailContent emailContent = emailContentFactory.createEmail("serviceactivation",variables);
        assertEquals("Service Activation Alert", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
    }


    //Test the OTP generation
    @Test
    public void testGenerateOTP() {
        int otp = emailService.generateOTP();
        assertTrue(otp >= 100000 && otp <= 999999);
    }


    //Test the case where OTP verification fails
    @Test
    public void testVerifyOTP_Failure() {
        String recipient = "test@email.com";
        int otp = emailService.generateOTP();

        assertFalse(emailService.verifyOTP(recipient, otp));
    }
}
