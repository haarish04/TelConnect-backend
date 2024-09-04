package com.example.TelConnect.model;
import jakarta.persistence.*;


@Entity
@Table(name = "customer_aadhar")
public class CustomerAadhar {
    @Id
    private Long id;
    @Column
    private String name;
    @Column(name = "id_verification")
    private String idVerification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdVerification() {
        return idVerification;
    }

    public void setIdVerification(String idVerification) {
        this.idVerification = idVerification;
    }

    public CustomerAadhar() {
    }
}