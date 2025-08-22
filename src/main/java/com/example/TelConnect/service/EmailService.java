package com.example.TelConnect.service;

import com.example.TelConnect.DTO.SecretsCache;
import com.example.TelConnect.model.EmailContent;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class EmailService {

    private final ConcurrentMap<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private final Counter emailSuccessCounter;
    private final Counter emailFailureCounter;

    @Autowired
    private final SecretsCache secretsCache;

    @Autowired
    private final EmailContentFactory emailFactory;

    public EmailService(EmailContentFactory emailFactory, SecretsCache secretsCache, MeterRegistry meterRegistry) {
        this.emailFactory = emailFactory;
        this.secretsCache= secretsCache;
        this.emailSuccessCounter = meterRegistry.counter("app_email_sent_success_total", "description", "Total number of successfully sent emails since app bootup");
        this.emailFailureCounter = meterRegistry.counter("app_email_sent_failure_total", "description", "Total number of failed email attempts since app bootup");

    }

    private static class OtpEntry {
        private final int otp;
        private final long timestamp;

        public OtpEntry(int otp) {
            this.otp = otp;
            this.timestamp = Instant.now().toEpochMilli();
        }

        public int getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    public int generateOTP() {
        return 100000 + new Random().nextInt(900000);
    }

    public boolean verifyOTP(String recipient, int otp) {
        OtpEntry entry = otpStore.get(recipient);
        long OTP_EXPIRY_DURATION = 300_000;
        if (entry == null || Instant.now().toEpochMilli() - entry.getTimestamp() > OTP_EXPIRY_DURATION) {
            otpStore.remove(recipient);
            return false;
        }
        return entry.getOtp() == otp;
    }

    public boolean customEmailSender(Map<String, Object> variables) {
        String type = (String) variables.get("type");
        String recipient = (String) variables.get("recipient");
        String name = (String) variables.get("name");
        Integer otp = (Integer) variables.get("otp");

        EmailContent email = emailFactory.createEmail(type, variables);

        try {
            boolean response=sendMail(email, recipient, name);
            if ("otp".equalsIgnoreCase(type) && otp != null) {
                otpStore.put(recipient, new OtpEntry(otp));
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean sendMail(EmailContent email, String recipient, String name) throws MailjetException {
        MailjetClient client = new MailjetClient(ClientOptions.builder()
                .apiKey(secretsCache.getSecret("APIKEY"))
                .apiSecretKey(secretsCache.getSecret("SECRETKEY"))
                .build());

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "telconnecta@gmail.com")
                                        .put("Name", "Telconnect Admin"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", recipient)
                                                .put("Name", name)))
                                .put(Emailv31.Message.SUBJECT, email.getSubject())
                                .put(Emailv31.Message.TEXTPART, email.getTextPart())
                                .put(Emailv31.Message.HTMLPART, email.getHtmlPart())
                                .put(Emailv31.Message.CUSTOMID, "PushEmail")));

        MailjetResponse response = client.post(request);
        if(response.getStatus()==200) {
            emailSuccessCounter.increment();
            return true;
        }
        else {
            emailFailureCounter.increment();
            return false;
        }
    }
}
