package com.example.TelConnect.repository;

import com.example.TelConnect.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<Incident ,String> {
}
