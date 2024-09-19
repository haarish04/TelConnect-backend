# TelConnect Backend

TelConnect is a backend application for onboarding customer and manage service plans. It provides user authentication, customer management, notification management, and service plan mapping using RESTful APIs.

## Table of Contents

- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)

## Project Overview

TelConnect is designed to provide a platform for onboarding and managing customers, their services, and notifications. It includes:
- User authentication using Spring Security.
- Password encryption using BCrypt.
- Managing customer notifications and service plans.
- Email sending service for verification and notifications.

## Technologies Used

- **Java** (JDK 17)
- **Spring Boot** (RESTful APIs, Dependency Injection)
- **Spring Security** (Authentication and Authorization)
- **Spring Data JPA** (Data Persistence)
- **MySQL/** (Database)
- **JUnit** (Test cases)
- **Swagger-UI** (API Documentation)
- **MailJet** (3rd Party Mailing Service)

## Features

- User registration and login with encrypted passwords.
- Manage customer data including personal information and service subscriptions.
- Map customers to their selected service plans.
- View and manage customer notifications.
- Send emails using MailJet for verification.
- Swagger UI for API documentation.

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

Access the API documentation at: http://localhost:8082/swagger-ui/index.html
