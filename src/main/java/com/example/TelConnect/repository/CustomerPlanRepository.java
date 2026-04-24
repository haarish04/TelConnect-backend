package com.example.TelConnect.repository;

import com.example.TelConnect.model.CustomerPlanMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerPlanRepository extends JpaRepository<CustomerPlanMapping, Long> {
    List<CustomerPlanMapping> findByCustomerId(Long customerId);
    List<CustomerPlanMapping> findByPlanId(String planId);
}
