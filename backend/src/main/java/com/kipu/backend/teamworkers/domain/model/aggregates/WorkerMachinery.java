package com.kipu.backend.teamworkers.domain.model.aggregates;

import lombok.Getter;

@Getter
public class WorkerMachinery {
    private Long id;
    private String machineryId;
    private String fullName;

    protected WorkerMachinery() {
    }

    public WorkerMachinery(String machineryId, String fullName) {
        if (machineryId == null || machineryId.isBlank()) {
            throw new IllegalArgumentException("Machinery ID is required");
        }
        this.machineryId = machineryId;
        this.fullName = fullName;
    }
}