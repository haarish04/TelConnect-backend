package com.example.TelConnect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Entity
@Table(name = "Verification_Requests")
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "documentId", nullable = false)
    private Document document;

    @Column
    @NotNull(message = "Request date cannot be null")
    private LocalDateTime requestDate;

    @Column
    @NotNull(message = "Status cannot be null")
    private String requestStatus;

    public Long getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(Long verificationId) {
        this.verificationId = verificationId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
