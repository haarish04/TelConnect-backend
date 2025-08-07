package com.example.TelConnect.DTO;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RegisterCustomerDTO {

    @NotNull(message = "Name cannot be null")
    private String customerName;

    private String password;

    @NotNull(message = "Email cannot be null")
    private String customerEmail;

    private String customerPhno;

    @NotNull(message = "Address cannot be null")
    private String customerAddress;

    private LocalDate customerDOB;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(LocalDate customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhno() {
        return customerPhno;
    }

    public void setCustomerPhno(String customerPhno) {
        this.customerPhno = customerPhno;
    }

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
