package com.kipu.backend.rnc.interfaces.rest.transform;

import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.interfaces.rest.resources.NonConformityRecordResource;
import com.kipu.backend.rnc.interfaces.rest.resources.SolutionLogResource;

import java.util.stream.Collectors;

/**
 * Assembler to convert a Domain Aggregate into an Output Resource.
 */
public class NonConformityRecordResourceFromEntityAssembler {
    public static NonConformityRecordResource toResourceFromEntity(NonConformityRecord entity) {
        var solutionLogs = entity.getSolutionNotes().stream()
                .map(log -> new SolutionLogResource(log.date(), log.note(), log.author().value()))
                .collect(Collectors.toList());

        return new NonConformityRecordResource(
                entity.getId(),
                entity.getProjectId().value(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSpecialty().name(),
                entity.getLocation(),
                entity.getSeverity().name(),
                entity.getStatus().name(),
                entity.getReportedBy().value(),
                entity.getReportDate(),
                entity.getImages(),
                entity.getAssignedTo() != null ? entity.getAssignedTo().value() : null,
                entity.getResolutionDate(),
                solutionLogs
        );
    }
}