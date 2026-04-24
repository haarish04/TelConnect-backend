package com.example.TelConnect.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TelConnect.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerEmail(String email);

    @Query("SELECT c.cidn FROM Customer c WHERE c.id IN :ids")
    List<String> findCidnByCustomerIds(@Param("ids") List<Long> ids);

}