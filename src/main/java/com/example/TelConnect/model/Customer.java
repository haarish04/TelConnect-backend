package com.example.TelConnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String customerEmail;

    @Column(name = "customer_phno")
    private String customerPhno;

    @Column(name = "account_creation_date", nullable = false)
    private LocalDate accountCreationDate;

    @Column(name = "role")
    private String role;


    // Constructors
    public Customer() {
        this.accountCreationDate = LocalDate.now();
    }

    public Customer(String customerName, String password, String customerEmail, String customerPhno) {
        this.customerName = customerName;
        this.password = password;
        this.customerEmail = customerEmail;
        this.customerPhno = customerPhno;
        this.accountCreationDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhno() {
        return customerPhno;
    }

    public void setCustomerPhno(String customerPhno) {
        this.customerPhno = customerPhno;
    }

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDate accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role= role;
    }

    // Override toString for better logging or debugging
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPhno='" + customerPhno + '\'' +
                ", accountCreationDate=" + accountCreationDate +
                ", roles=" + role +
                '}';
    }
}
