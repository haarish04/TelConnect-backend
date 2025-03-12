# TelConnect Backend

TelConnect is a backend application for onboarding customer and manage service plans. It provides user authentication, customer management, notification management, and service plan mapping using RESTful APIs.

## Table of Contents

- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)


## Installation
- Clone the repository:
   ```bash
   git clone https://github.com/haarish04/telconnect.git
   cd telconnect-backend
   mvn clean install

## Environment Variables

### Configure environment variables:

- SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/telconnect
- SPRING_DATASOURCE_USERNAME=root
- SPRING_DATASOURCE_PASSWORD=password
- MJ_APIKEY= apikey
- MJ_SECRETKEY= secretKey

## Running the Application

- Run command
   ```bash
   mvn spring-boot:run

## API Documentation
Start the server and access the API documentation at: http://localhost:8082/swagger-ui/index.html
