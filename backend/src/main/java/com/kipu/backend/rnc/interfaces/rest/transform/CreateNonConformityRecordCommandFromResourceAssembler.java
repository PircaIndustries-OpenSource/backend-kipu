package com.kipu.backend.rnc.interfaces.rest.transform;

import com.kipu.backend.rnc.application.commands.CreateNonConformityRecordCommand;
import com.kipu.backend.rnc.interfaces.rest.resources.CreateNonConformityRecordResource;

/**
 * Assembler to convert a Create Resource into a Create Command.
 */
public class CreateNonConformityRecordCommandFromResourceAssembler {
    public static CreateNonConformityRecordCommand toCommandFromResource(CreateNonConformityRecordResource resource) {
        return new CreateNonConformityRecordCommand(
                resource.projectId(),
                resource.title(),
                resource.description(),
                resource.specialty(),
                resource.location(),
                resource.severity(),
                resource.reportedBy(),
                resource.images()
        );
    }
}