FROM openjdk:8
COPY target/billing-*.jar /billing-system.jar
CMD ["java","-cp", "/*.jar", "com.example.TelConnect.TelConnectApplication"]