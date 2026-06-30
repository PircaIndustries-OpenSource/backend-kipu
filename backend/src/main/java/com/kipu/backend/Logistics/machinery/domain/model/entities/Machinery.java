package com.kipu.backend.logistics.machinery.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machinery {
    private String id;
    private String name;
    private String status; // 'IN_USE' | 'URGENT_MAINTENANCE' | 'AVAILABLE'
    private String assignedTo;
    private String registrationDate;
    private String maintenanceHours;
    private String assignmentDetail;
    private String projectId;

    public Machinery() {
    }

    public Machinery(String name, String status, String assignedTo, String registrationDate, String maintenanceHours, String assignmentDetail, String projectId) {
        this.name = name;
        this.status = status;
        this.assignedTo = assignedTo;
        this.registrationDate = registrationDate;
        this.maintenanceHours = maintenanceHours;
        this.assignmentDetail = assignmentDetail;
        this.projectId = projectId;
    }
}
