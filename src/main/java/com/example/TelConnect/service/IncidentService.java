package com.example.TelConnect.service;

import com.example.TelConnect.DTO.IncidentDTO;
import com.example.TelConnect.model.Incident;
import com.example.TelConnect.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<IncidentDTO> readIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public IncidentDTO createIncident(IncidentDTO dto) {

        long count = incidentRepository.count() + 1;
        String newId = String.format("INC%02d", count);

        Incident incident = new Incident();
        incident.setIncident_id(newId);
        incident.setCustomer_id(dto.getCustomer_id());
        incident.setDescription(dto.getDescription());
        incident.setStatus(dto.getStatus());
        incident.setPriority(dto.getPriority());
        incident.setAssigned_to(dto.getAssigned_to());

        Incident saved = incidentRepository.save(incident);
        return mapToDTO(saved);
    }

    public IncidentDTO updateIncident(String incident_id, IncidentDTO dto) {

        Incident existing = incidentRepository.findById(incident_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

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

        Incident saved = incidentRepository.save(existing);
        return mapToDTO(saved);
    }

    public void deleteIncident(String incident_id) {
        Incident existing = incidentRepository.findById(incident_id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        incidentRepository.delete(existing);
    }

    private IncidentDTO mapToDTO(Incident incident) {
        IncidentDTO dto = new IncidentDTO();
        dto.setIncident_id(incident.getIncident_id());
        dto.setCustomer_id(incident.getCustomer_id());
        dto.setDescription(incident.getDescription());
        dto.setStatus(incident.getStatus());
        dto.setPriority(incident.getPriority());
        dto.setAssigned_to(incident.getAssigned_to());
        return dto;
    }
}
