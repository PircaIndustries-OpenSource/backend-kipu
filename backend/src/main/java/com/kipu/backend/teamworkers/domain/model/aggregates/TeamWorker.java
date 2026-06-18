package com.kipu.backend.teamworkers.domain.model.aggregates;

import com.kipu.backend.shared.domain.exceptions.BusinessException;
import com.kipu.backend.teamworkers.domain.model.exceptions.DomainValidationException;
import com.kipu.backend.teamworkers.domain.model.valueobjects.WorkerId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class TeamWorker {
    private WorkerId id;
    private String dni;
    private String fullName;
    private String role;
    private boolean isActive;
    private String projectId;

    private final List<WorkerMachinery> machineries;

    protected TeamWorker() {
        this.machineries = new ArrayList<>();
    }

    public TeamWorker(String dni, String fullName, String role, String projectId) {
        this();
        this.id = new WorkerId();
        this.dni = dni;
        this.fullName = fullName;
        this.role = role;
        this.projectId = projectId;
        this.isActive = true;
    }

    public TeamWorker(WorkerId id, String dni, String fullName, String role, boolean isActive, String projectId) {
        this();
        this.id = id;
        this.dni = dni;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
        this.projectId = projectId;
    }

    public void assignMachinery(String machineryId, String fullName) {
        boolean alreadyAssigned = this.machineries.stream()
                .anyMatch(m -> m.getMachineryId().equals(machineryId));

        if (alreadyAssigned) {
            throw new BusinessException("worker.validation.machineryAlreadyAssigned");
        }

        this.machineries.add(new WorkerMachinery(machineryId, fullName));
    }

    public void removeMachinery(String machineryId) {
        this.machineries.removeIf(m -> m.getMachineryId().equals(machineryId));
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public List<WorkerMachinery> getMachineries() {
        return Collections.unmodifiableList(machineries);
    }


}