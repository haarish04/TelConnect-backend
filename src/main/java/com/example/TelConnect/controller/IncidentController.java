package com.example.TelConnect.controller;

import com.example.TelConnect.model.Incident;
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
    public ResponseEntity<List<Incident>> readIncidents() {
        List<Incident> incidents = incidentService.readincidents();
        return ResponseEntity.ok(incidents);
    }

    @PostMapping("/incidents")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        Incident createdIncident = incidentService.createIncident(incident);
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

    @PutMapping("/incidents/{incident_id}")
    public ResponseEntity<Incident> updateIncident( @PathVariable String incident_id, @RequestBody Incident incident) {
        Incident updatedIncident = incidentService.updateIncident(incident_id, incident);
        return ResponseEntity.ok(updatedIncident);
    }

    @DeleteMapping("/incidents/{incident_id}")
    public ResponseEntity<String> deleteIncident(@PathVariable String incident_id) {
        incidentService.deleteIncident(incident_id);
        return ResponseEntity.ok("Incident deteled");
    }
}