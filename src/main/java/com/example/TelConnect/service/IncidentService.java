package com.example.TelConnect.service;

import com.example.TelConnect.DTO.IncidentDTO;
import com.example.TelConnect.model.Incident;
import com.example.TelConnect.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<Incident> readIncidents() {
        List<Incident> incidentsList= incidentRepository.findAll();
        return new ArrayList<>(incidentsList);
    }

    public Incident createIncident(IncidentDTO dto) {

        long count = incidentRepository.count() + 1;
        String newId = String.format("INC%02d", count);

        Incident incident = new Incident();
        incident.setIncident_id(newId);
        incident.setCustomer_id(dto.getCustomer_id());
        incident.setDescription(dto.getDescription());
        incident.setStatus(dto.getStatus());
        incident.setPriority(dto.getPriority());
        incident.setAssigned_to(dto.getAssigned_to());

        return incidentRepository.save(incident);
    }

    public Incident updateIncident(String incident_id, IncidentDTO dto) {

        Incident existing = incidentRepository.findById(incident_id).orElse(null);
        if(existing==null)
            return null;

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            existing.setPriority(dto.getPriority());
        }
        if (dto.getAssigned_to() != null && !dto.getAssigned_to().isEmpty()) {
            existing.setAssigned_to(dto.getAssigned_to());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }

        return incidentRepository.save(existing);
    }

    public void deleteIncident(String incident_id) {
        Incident existing = incidentRepository.findById(incident_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        incidentRepository.delete(existing);
    }

}
