package com.example.TelConnect.repository;

import com.example.TelConnect.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    List<Verification> findByCustomerId(Long customerId);
}
