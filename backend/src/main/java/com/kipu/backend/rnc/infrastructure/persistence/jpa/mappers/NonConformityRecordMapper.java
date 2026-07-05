package com.kipu.backend.rnc.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.domain.model.valueobjects.SolutionLog;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.rnc.domain.model.valueobjects.external.UserId;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.embeddables.SolutionLogEmbeddable;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.entities.NonConformityRecordJpaEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * Bidirectional mapper between Domain Aggregate and JPA Entity.
 */
public class NonConformityRecordMapper {

    public static NonConformityRecordJpaEntity toEntity(NonConformityRecord domain) {
        NonConformityRecordJpaEntity entity = new NonConformityRecordJpaEntity();
        entity.setId(domain.getId());
        entity.setProjectId(domain.getProjectId().value());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setSpecialty(domain.getSpecialty());
        entity.setLocation(domain.getLocation());
        entity.setSeverity(domain.getSeverity());
        entity.setStatus(domain.getStatus());
        entity.setReportedBy(domain.getReportedBy().value());
        entity.setReportDate(domain.getReportDate());
        entity.setImages(domain.getImages());
        entity.setAssignedTo(domain.getAssignedTo() != null ? domain.getAssignedTo().value() : null);
        entity.setResolutionDate(domain.getResolutionDate());

        if (domain.getSolutionNotes() != null) {
            entity.setSolutionNotes(domain.getSolutionNotes().stream()
                    .map(log -> new SolutionLogEmbeddable(log.date(), log.note(), log.author().value()))
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    public static NonConformityRecord toDomain(NonConformityRecordJpaEntity entity) {
        try {
            // Rehydrate using private no-args constructor via reflection to protect domain invariants
            Constructor<NonConformityRecord> constructor = NonConformityRecord.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            NonConformityRecord domain = constructor.newInstance();

            setField(domain, "id", entity.getId());
            setField(domain, "projectId", new ProjectId(entity.getProjectId()));
            setField(domain, "title", entity.getTitle());
            setField(domain, "description", entity.getDescription());
            setField(domain, "specialty", entity.getSpecialty());
            setField(domain, "location", entity.getLocation());
            setField(domain, "severity", entity.getSeverity());
            setField(domain, "status", entity.getStatus());
            setField(domain, "reportedBy", new UserId(entity.getReportedBy()));
            setField(domain, "reportDate", entity.getReportDate());
            setField(domain, "images", entity.getImages());

            if (entity.getAssignedTo() != null) {
                setField(domain, "assignedTo", new UserId(entity.getAssignedTo()));
            }

            setField(domain, "resolutionDate", entity.getResolutionDate());

            if (entity.getSolutionNotes() != null) {
                setField(domain, "solutionNotes", entity.getSolutionNotes().stream()
                        .map(embeddable -> new SolutionLog(embeddable.getLogDate(), embeddable.getNote(), new UserId(embeddable.getAuthorId())))
                        .collect(Collectors.toList()));
            }

            return domain;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping NonConformityRecordJpaEntity to Domain", e);
        }
    }

    private static void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}