package com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands;

public record DeleteConcreteCuringSensorCommand(Long id) {
    public DeleteConcreteCuringSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
    }
}
