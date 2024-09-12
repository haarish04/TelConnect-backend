FROM openjdk:8
COPY target/billing-*.jar /billing-system.jar
CMD ["java","-cp", "/billing-system.jar", "com.telstra.assignment.billing_system.BillingSystemApplication"]