package com.example.TelConnect.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.TelConnect.model.EmailContent;
import com.example.TelConnect.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        emailService = new EmailService(mock(NotificationService.class), mock(NotificationRepository.class));
    }

    //Test the email method for packaging welcome message
    @Test
    public void testWelcomeMessage() {
        EmailContent emailContent = emailService.WelcomeMessage();

        assertEquals("Welcome to TelConnect! Your Connection Starts Here", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
        assertNotNull(emailContent.getTextPart());
    }

    //Test the email method for packaging OTP message
    @Test
    public void testOTPMessage() {
        int otp = 123456;
        EmailContent emailContent = emailService.OTPMessage(otp);

        assertEquals("123456 is your 2FA OTP", emailContent.getSubject());
        assertTrue(emailContent.getHtmlPart().contains(String.valueOf(otp)));
    }

    //Test the email method for packaging thank you message
    @Test
    public void testThankYouMessage() {
        EmailContent emailContent = emailService.thankYouMessage();

        assertEquals("Thank You for Choosing TelConnect - Connecting You to What Matters!", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
    }

    //Test the email method for packaging activation message
    @Test
    public void testServiceActivationMessage() {
        EmailContent emailContent = emailService.ServiceActivationMessage();

        assertEquals("Service Activation", emailContent.getSubject());
        assertNotNull(emailContent.getHtmlPart());
    }

    //Test the custom Email Sender to package the appropriate email for "welcome" case
    @Test
    public void testCustomEmailSender_Welcome() {
        String recipient = "test@email.com";
        String name = "Test User";
        boolean result = emailService.customEmailSender("welcome", null, recipient, name);

        assertTrue(result);
    }

    //Test the custom Email Sender to package the appropriate email for "OTP" case
    @Test
    public void testCustomEmailSender_OTP() {
        String recipient = "test@email.com";
        String name = "Test User";
        int otp = emailService.generateOTP();
        boolean result = emailService.customEmailSender("otp", otp, recipient, name);

        assertTrue(result);
    }

    //Test the custom Email Sender to package the appropriate email for "thankYou" case
    @Test
    public void testCustomEmailSender_ThankYou() {
        String recipient = "test@email.com";
        String name = "Test User";
        boolean result = emailService.customEmailSender("thankYou", null, recipient, name);

        assertTrue(result);
    }

    //Test the custom Email Sender to package the appropriate email for "activation" case
    @Test
    public void testCustomEmailSender_ServiceActivation() {
        String recipient = "1ms20cs049@email.com";
        String name = "Test User";
        boolean result = emailService.customEmailSender("serviceActivation", null, recipient, name);

        assertTrue(result);
    }

    //Test the OTP generation
    @Test
    public void testGenerateOTP() {
        int otp = emailService.generateOTP();
        assertTrue(otp >= 100000 && otp <= 999999);
    }

    //Test to verify OTP
    @Test
    public void testVerifyOTP_Success() {
        String recipient = "1ms20cs049@email.com";
        int otp = emailService.generateOTP();
        emailService.customEmailSender("otp", otp, recipient, "Test User");

        assertTrue(emailService.verifyOTP(recipient, otp));
    }

    //Test the case where OTP verification fails
    @Test
    public void testVerifyOTP_Failure() {
        String recipient = "1ms20cs049@email.com";
        int otp = emailService.generateOTP();

        assertFalse(emailService.verifyOTP(recipient, otp));
    }
}
