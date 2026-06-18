package com.kipu.backend.teamworkers.domain.model.aggregates;

import com.kipu.backend.teamworkers.domain.model.exceptions.DomainValidationException;
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
            throw new DomainValidationException("worker.validation.machineryIdRequired");
        }
        this.machineryId = machineryId;
        this.fullName = fullName;
    }
}