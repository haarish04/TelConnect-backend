package com.example.TelConnect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "customer_plans_mapping")
public class CustomerPlanMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerPlanId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", referencedColumnName = "planId", nullable = false)
    private ServicePlan plan;

    @Column
    @NotNull(message = "Start date of plan cannot be null")
    private LocalDate startDate;

    @Column
    @NotNull(message = "End date of plan cannot be null")
    private LocalDate endDate;

    @Column
    @NotNull(message = "Status of plan cannot be null")
    private String status;

    public Long getCustomerPlanId() {
        return customerPlanId;
    }

    public void setCustomerPlanId(Long customerPlanId) {
        this.customerPlanId = customerPlanId;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServicePlan getPlan() {
        return plan;
    }

    public void setPlan(ServicePlan plan) {
        this.plan = plan;
    }
}