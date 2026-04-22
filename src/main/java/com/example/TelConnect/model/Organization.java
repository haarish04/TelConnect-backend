package com.example.TelConnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @Column(name = "cidn")
    private Long CIDN;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "date")
    private LocalDate enrolledDate = LocalDate.now();

    @Column(name = "org_address")
    private String orgAddress;

    public Long getCIDN() {
        return CIDN;
    }

    public void setCIDN(Long CIDN) {
        this.CIDN = CIDN;
    }

    public LocalDate getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(LocalDate enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }
}
