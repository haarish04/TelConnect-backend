package com.example.TelConnect.controller;

import com.example.TelConnect.DTO.IncidentDTO;
import com.example.TelConnect.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TelConnect.model.Incident;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    //Get all incidents
    @GetMapping("/incidents")
    public ResponseEntity<List<Incident>> readIncidents() {
        return ResponseEntity.ok(incidentService.readIncidents());
    }

    //Handler to create new incident
    @PostMapping("/incidents")
    public ResponseEntity<Incident> createIncident(@RequestBody IncidentDTO incidentDTO) {

        Incident newIncident=incidentService.createIncident(incidentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newIncident);
    }

    //Handler for update Incident
    @PutMapping("/incidents/{incident_id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable String incident_id, @RequestBody IncidentDTO incidentDTO) {

        Incident updated =incidentService.updateIncident(incident_id, incidentDTO);
        if(updated==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok(updated);
    }

    //Handler to delete incident
    @DeleteMapping("/incidents/{incident_id}")
    public ResponseEntity<String> deleteIncident(
            @PathVariable String incident_id) {

        incidentService.deleteIncident(incident_id);
        return ResponseEntity.ok("Incident deleted successfully");
    }
}
