package com.example.TelConnect.DTO;


import com.example.TelConnect.enums.Priority;
import com.example.TelConnect.enums.Status;
import lombok.Data;

@Data
public class IncidentDTO {

    private Long customer_id;
    private String description;
    private Status status;
    private Priority priority;
    private String assigned_to;
}