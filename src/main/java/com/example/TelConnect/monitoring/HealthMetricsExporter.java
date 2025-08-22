package com.example.TelConnect.monitoring;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.stereotype.Component;

@Component
public class HealthMetricsExporter {

    public HealthMetricsExporter(MeterRegistry registry, HealthEndpoint healthEndpoint) {

        // Database health
        Gauge.builder("health_db", healthEndpoint,
                        h -> toStatusCode((Health) healthEndpoint.healthForPath("db")))
                .description("Database health status")
                .register(registry);

        // Vault health
        Gauge.builder("health_vault", healthEndpoint,
                        h -> toStatusCode((Health) healthEndpoint.healthForPath("vault")))
                .description("Vault health status")
                .register(registry);

        // MailJet health
        Gauge.builder("health_mailjet", healthEndpoint,
                        h -> toStatusCode((Health) healthEndpoint.healthForPath("mailJet")))
                .description("MailJet health status")
                .register(registry);

    }
    private double toStatusCode(Health health) {
        if (health == null) return 0;
        return "UP".equals(health.getStatus().getCode()) ? 1 : 0;
    }
}