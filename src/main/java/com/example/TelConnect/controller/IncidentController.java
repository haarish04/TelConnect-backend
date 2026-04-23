package com.example.TelConnect.controller;

import com.example.TelConnect.DTO.IncidentDTO;
import com.example.TelConnect.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentDTO>> readIncidents() {
        return ResponseEntity.ok(incidentService.readIncidents());
    }

    @PostMapping("/incidents")
    public ResponseEntity<IncidentDTO> createIncident(
            @RequestBody IncidentDTO incidentDTO) {

        IncidentDTO created = incidentService.createIncident(incidentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/incidents/{incident_id}")
    public ResponseEntity<IncidentDTO> updateIncident(
            @PathVariable String incident_id,
            @RequestBody IncidentDTO incidentDTO) {

        IncidentDTO updated =
                incidentService.updateIncident(incident_id, incidentDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/incidents/{incident_id}")
    public ResponseEntity<String> deleteIncident(
            @PathVariable String incident_id) {

        incidentService.deleteIncident(incident_id);
        return ResponseEntity.ok("Incident deleted successfully");
    }
}