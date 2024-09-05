package com.example.TelConnect.service;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Emailv31;
import java.util.Random;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.TelConnect.repository.NotificationRepository;
import com.example.TelConnect.model.Notification;
import com.example.TelConnect.service.NotificationService;
import com.example.TelConnect.model.EmailContent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class EmailService {

    private final ConcurrentMap<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    public EmailContent email;

    public EmailService(NotificationService notificationService, NotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
        this.notificationService=notificationService;
    }

    public EmailContent WelcomeMessage(){
        EmailContent email= new EmailContent();
        email.setSubject("Welcome to TelConnect");
        email.setTextPart("Welcome to TelConnect! We're thrilled to have you as part of our mission to help you stay connected with your family and the people who matter most. Thank you for choosing us to strengthen the bonds that bring your world closer together!");
        email.setHtmlPart("<h3> Welcome aboard! </h3> <br/> We're thrilled to have you as part of our mission to help you stay connected with your family and the people who matter most. <br/> Thank you for choosing us to strengthen the bonds that bring your world closer together!");
        return email;
    }

    public EmailContent OTPMessage(int OTP){
        EmailContent email= new EmailContent();
        email.setSubject(OTP + "is your 2FA OTP");
        email.setTextPart("OTP to verify your email account is: "+ OTP);
        email.setHtmlPart("<h3>OTP to verify your email account is: <br/><h2>" + OTP + "</h2></h3>"+ "<br/><h3>I you did not initiate this request, someone may be trying to gain access to your account</h3>");
        return email;
    }

    public EmailContent thankYouMessage(){
        EmailContent email= new EmailContent();
        email.setSubject("Thank You for choosing us");
        email.setTextPart("Thank you for selecting TelConnect as your service provider! We appreciate your trust and are committed to delivering exceptional connectivity that brings you closer to the people who matter most. Your journey with us starts now, and we're here to ensure it’s a smooth ride!");
        email.setHtmlPart("<h3>Thank you for selecting TelConnect as your service provider! We appreciate your trust and are committed to delivering exceptional connectivity that brings you closer to the people who matter most.</h3>" + "<h3>Your journey with us starts now, and we're here to ensure it’s a smooth ride!</h3>");
        return email;
    }

    public EmailContent ServiceActivationMessage(){
        EmailContent email= new EmailContent();
        email.setSubject("Service Activation");
        email.setTextPart("Your selected plan has been activated on your mobile number. Reach out to customer support if you are having trouble using our services.");
        email.setHtmlPart("<h3>Your selected plan has been activated on your mobile number</h3>"+"</br><h3>Reach out to customer support if you are having trouble using our services</h3>");
        return email;
    }

    public boolean customEmailSender(String type,Integer OTP, String recipient, String name) {
        EmailContent mail;
        switch (type.toLowerCase()) {
            case "welcome":
                mail= WelcomeMessage();
                try{
                    sendMail(mail,recipient,name);
                    return true;
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

            case "otp":
                if (OTP != 0) {
                    mail= OTPMessage( OTP);
                    try {
                        sendMail(mail,recipient,name);
                        otpStore.put(recipient, OTP);
                        return true;
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                } else {
                    System.out.println("No OTP ");
                }

            case "thankyou":
                mail= thankYouMessage();
                try{
                    sendMail(mail,recipient,name);
                    return true;
                } catch (MailjetException e) {
                    e.printStackTrace();
                }
                break;

            case "serviceactivation":
                mail=ServiceActivationMessage();
                try{
                    sendMail(mail,recipient,name);
                    return true;
                } catch (MailjetException e) {
                    e.printStackTrace();
                }

            default:
                System.out.println("Error");
                return false;
        }
        return false;
    }

    public void sendMail(EmailContent email, String recipient, String name) throws MailjetException {

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        String apiKey = System.getenv("MJ_APIKEY");
        String apiSecretKey = System.getenv("MJ_SECRETKEY");
        client = new MailjetClient(apiKey,apiSecretKey);
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "telconnecta@gmail.com")
                                        .put("Name", "Telconnect admin"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", recipient)
                                                .put("Name", name)))
                                .put(Emailv31.Message.SUBJECT, email.getSubject())
                                .put(Emailv31.Message.TEXTPART, email.getTextPart())
                                .put(Emailv31.Message.HTMLPART, email.getHtmlPart())
                                .put(Emailv31.Message.CUSTOMID, "PushEmail")));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }

    public int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public boolean verifyOTP(String recipient, int otp) {
        Integer storedOtp = otpStore.get(recipient);
        return storedOtp != null && storedOtp.equals(otp);
    }
}
