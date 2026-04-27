package com.example.TelConnect.model;

import com.example.TelConnect.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "UserWhitelist")
public class UserWhitelist {
//    customer_id, status, EnrollmentDate
    @Id
    private Long customerId;

    @Column
    private String status;

    @Column
    private LocalDate enrollmentDate;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "UserWhitelist{" +
                "customerId=" + customerId +
                ", status='" + status + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
