package com.kipu.backend.team.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invitation {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String status; // PENDING, ACCEPTED, REJECTED
    private String projectId;

    public Invitation() {
    }

    public Invitation(String email, String firstName, String lastName, String role, String status, String projectId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
        this.projectId = projectId;
    }
}
