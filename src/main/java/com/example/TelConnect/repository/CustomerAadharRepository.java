package com.example.TelConnect.repository;
import com.example.TelConnect.model.CustomerAadhar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAadharRepository  extends JpaRepository<CustomerAadhar, Long> {

    List<CustomerAadhar> findByIdVerificationIn(List<String> idVerifications);
}
