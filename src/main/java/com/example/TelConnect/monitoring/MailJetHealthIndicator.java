package com.example.TelConnect.monitoring;

import com.example.TelConnect.DTO.SecretsCache;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MailJetHealthIndicator implements HealthIndicator {

    @Autowired
    private final SecretsCache secretsCache;

    public MailJetHealthIndicator(SecretsCache secretsCache) {
        this.secretsCache = secretsCache;
    }

    @Override
    public Health health() {
        try {
            boolean mailJetAvailable = pingMailJet();
            if (mailJetAvailable) {
                return Health.up().withDetail("MailJet", "Available").build();
            } else {
                return Health.down().withDetail("MailJet", "Unavailable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("MailJet", "Error").build();
        }
    }

    private boolean pingMailJet() {
        try {
            MailjetClient client = new MailjetClient(ClientOptions.builder()
                    .apiKey(secretsCache.getSecret("APIKEY"))
                    .apiSecretKey(secretsCache.getSecret("SECRETKEY"))
                    .build());

            MailjetRequest request = new MailjetRequest(User.resource);
            MailjetResponse response = client.get(request);

            int status = response.getStatus();
            return status == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

