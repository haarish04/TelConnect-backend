package com.example.TelConnect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Service_Plans")
public class ServicePlan {

    @Id
    @NotNull(message = "PlanId cannot be null")
    private String planId;

    @Column
    @NotNull(message = "Plan name cannot be null")
    private String planName;

    @Column
    @NotNull(message = "Plan price cannot be null")
    private String planPrice;

    @Column
    private String planDescription;

    @Column
    @NotNull(message = "Duration cannot be null")
    private String planDuration;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanDuration() {
        return planDuration;
    }

    public void setPlanDuration(String planDuration) {
        this.planDuration = planDuration;
    }

    @Override
    public String toString() {
        return "ServicePlan{" +
                "planId='" + planId + '\'' +
                ", planName='" + planName + '\'' +
                ", planPrice='" + planPrice + '\'' +
                ", planDescription='" + planDescription + '\'' +
                ", planDuration='" + planDuration + '\'' +
                '}';
    }
}