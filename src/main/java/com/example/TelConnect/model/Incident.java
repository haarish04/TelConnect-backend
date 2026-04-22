package com.example.TelConnect.model;

import com.example.TelConnect.enums.Priority;
import com.example.TelConnect.enums.Status;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "incidents")
public class Incident {

    @Id
    private String incident_id;

    @Column
    private Long customer_id;

    @Column
    private LocalDateTime date_time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column
    private String assigned_to;

    @PrePersist
    public void prePersist() {
        this.date_time = LocalDateTime.now();
        if (this.assigned_to == null || this.assigned_to.isEmpty()) {
            this.assigned_to = "admin";
        }
    }

    @Override
    public String toString() {
        return "Incident{" +
                "incident_id='" + incident_id + '\'' +
                ", customer_id=" + customer_id +
                ", date_time=" + date_time +
                ", status=" + status +
                ", priority=" + priority +
                ", assigned_to='" + assigned_to + '\'' +
                '}';
    }
}

