package com.kipu.backend.documents.application.commands;

import com.kipu.backend.documents.domain.model.valueobjects.Signer;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record CreateDocumentCommand(
        String type,
        LocalDateTime deadline,
        String projectId,
        List<Signer> assignedUsers
) {
    public CreateDocumentCommand {
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Type is required");
        if (deadline == null) throw new IllegalArgumentException("Deadline is required");
        if (projectId == null || projectId.isBlank()) throw new IllegalArgumentException("Project ID is required");
        if (assignedUsers == null) assignedUsers = new ArrayList<>();
    }
}
