package com.example.TelConnect.model;
public class LoginRequest {
    private String customerEmail;
    private String password;

    // Getters and Setters
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
