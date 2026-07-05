package com.kipu.backend.rnc.application.internal.commandservices;

import com.kipu.backend.rnc.application.commands.AddSolutionNoteCommand;
import com.kipu.backend.rnc.application.commands.AssignNonConformityRecordCommand;
import com.kipu.backend.rnc.application.commands.CreateNonConformityRecordCommand;
import com.kipu.backend.rnc.application.commandservices.NonConformityRecordCommandService;
import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;
import com.kipu.backend.rnc.domain.model.valueobjects.Severity;
import com.kipu.backend.rnc.domain.model.valueobjects.Specialty;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.rnc.domain.model.valueobjects.external.UserId;
import com.kipu.backend.rnc.domain.repositories.NonConformityRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the command service for NonConformityRecord.
 * Orchestrates use cases by delegating business logic to the Aggregate Root.
 */
@Service
public class NonConformityRecordCommandServiceImpl implements NonConformityRecordCommandService {

    private final NonConformityRecordRepository repository;

    public NonConformityRecordCommandServiceImpl(NonConformityRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<NonConformityRecord> handle(CreateNonConformityRecordCommand command) {
        var rnc = new NonConformityRecord(
                new ProjectId(command.projectId()),
                command.title(),
                command.description(),
                Specialty.valueOf(command.specialty()),
                command.location(),
                Severity.valueOf(command.severity()),
                new UserId(command.reportedBy()),
                command.images()
        );
        return Optional.of(repository.save(rnc));
    }

    @Override
    public Optional<NonConformityRecord> handle(AssignNonConformityRecordCommand command) {
        var rncOptional = repository.findById(command.rncId());
        if (rncOptional.isEmpty()) {
            throw new IllegalArgumentException("RNC with ID " + command.rncId() + " not found.");
        }

        var rnc = rncOptional.get();
        rnc.assignTo(new UserId(command.assigneeId()));
        return Optional.of(repository.save(rnc));
    }

    @Override
    public Optional<NonConformityRecord> handle(AddSolutionNoteCommand command) {
        var rncOptional = repository.findById(command.rncId());
        if (rncOptional.isEmpty()) {
            throw new IllegalArgumentException("RNC with ID " + command.rncId() + " not found.");
        }

        var rnc = rncOptional.get();
        rnc.addSolutionNote(command.note(), new UserId(command.authorId()));
        return Optional.of(repository.save(rnc));
    }
}