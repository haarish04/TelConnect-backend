package com.example.TelConnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;


@Entity
@Table(name = "customer_plans_mapping")
public class CustomerPlanMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPlanId;

    @Column
    private Long customerId;

    @Column
    private String planId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String status;

    public Long getCustomerPlanId() {
        return customerPlanId;
    }

    public void setCustomerPlanId(Long customerPlanId) {
        this.customerPlanId = customerPlanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerPlanMapping{" +
                "customerPlanId=" + customerPlanId +
                ", customerId=" + customerId +
                ", planId=" + planId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}