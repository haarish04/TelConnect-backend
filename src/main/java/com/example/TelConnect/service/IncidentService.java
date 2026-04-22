
package com.example.TelConnect.service;

import com.example.TelConnect.model.Incident;
import com.example.TelConnect.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<Incident> readincidents() {
        return incidentRepository.findAll();
    }

    public Incident createIncident(Incident incident) {
        long count = incidentRepository.count() + 1;
        String newId = String.format("INC%02d", count);
        incident.setIncident_id(newId);
        return incidentRepository.save(incident);
    }

    public Incident updateIncident(String incident_id, Incident updatedIncident) {

        Incident existing = incidentRepository.findById(incident_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

        if (updatedIncident.getStatus() != null) {
            existing.setStatus(updatedIncident.getStatus());
        }
        if (updatedIncident.getPriority() != null) {
            existing.setPriority(updatedIncident.getPriority());
        }
        if (updatedIncident.getAssigned_to() != null &&
                !updatedIncident.getAssigned_to().isEmpty()) {
            existing.setAssigned_to(updatedIncident.getAssigned_to());
        }
        return incidentRepository.save(existing);
    }
    public void deleteIncident(String incident_id) {

        Incident existing = incidentRepository.findById(incident_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        incidentRepository.delete(existing);
    }
}

