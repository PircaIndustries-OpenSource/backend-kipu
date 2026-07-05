package com.kipu.backend.rnc.application.commandservices;

import com.kipu.backend.rnc.application.commands.AddSolutionNoteCommand;
import com.kipu.backend.rnc.application.commands.AssignNonConformityRecordCommand;
import com.kipu.backend.rnc.application.commands.CreateNonConformityRecordCommand;
import com.kipu.backend.rnc.domain.model.aggregates.NonConformityRecord;

import java.util.Optional;

/**
 * Interface defining the command operations for NonConformityRecord.
 */
public interface NonConformityRecordCommandService {
    Optional<NonConformityRecord> handle(CreateNonConformityRecordCommand command);
    Optional<NonConformityRecord> handle(AssignNonConformityRecordCommand command);
    Optional<NonConformityRecord> handle(AddSolutionNoteCommand command);
}