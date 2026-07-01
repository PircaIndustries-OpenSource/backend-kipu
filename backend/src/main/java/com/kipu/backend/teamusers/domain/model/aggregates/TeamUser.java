package com.kipu.backend.teamusers.domain.model.aggregates;

import com.kipu.backend.teamusers.domain.model.exceptions.InactiveRoleChangeException;
import com.kipu.backend.teamusers.domain.model.exceptions.UserActiveException;
import com.kipu.backend.teamusers.domain.model.exceptions.UserInactiveException;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TeamUser {
    @Setter
    private String id;
    private Long userId;
    private FullName fullName;
    private EmailAddress email;
    private String role;
    private boolean isActive;
    private String projectId;

    protected TeamUser() {}

    public TeamUser(Long userId, FullName fullName, EmailAddress email, String role, String projectId) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.projectId = projectId;
    }

    public TeamUser(String id, Long userId, FullName fullName, EmailAddress email, String role, String projectId) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.projectId = projectId;
    }

    public TeamUser(String id, Long userId, FullName fullName, EmailAddress email, String role, boolean isActive, String projectId) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
        this.projectId = projectId;
    }

    public void deactivate() {
        if (!this.isActive) {
            throw new UserInactiveException();
        }
        this.isActive = false;
    }

    public void activate() {
        if (this.isActive) {
            throw new UserActiveException();
        }
        this.isActive = true;
    }

    public void updateRole(String newRole) {
        if (!this.isActive) {
            throw new InactiveRoleChangeException();
        }
        this.role = newRole;
    }
}
