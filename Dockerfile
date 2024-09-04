# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/TelConnect-0.0.1-SNAPSHOT.jar /app/TelConnect-0.0.1-SNAPSHOT.jar

# Expose the port that the application runs on
EXPOSE 8082

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/TelConnect-0.0.1-SNAPSHOT.jar"]
