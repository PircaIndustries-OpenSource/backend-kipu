package com.kipu.backend.rnc.interfaces.rest;

import com.kipu.backend.rnc.application.commands.AddSolutionNoteCommand;
import com.kipu.backend.rnc.application.commands.AssignNonConformityRecordCommand;
import com.kipu.backend.rnc.application.commandservices.NonConformityRecordCommandService;
import com.kipu.backend.rnc.application.queries.GetAllNonConformityRecordsByProjectIdQuery;
import com.kipu.backend.rnc.application.queries.GetNonConformityRecordByIdQuery;
import com.kipu.backend.rnc.application.queryservices.NonConformityRecordQueryService;
import com.kipu.backend.rnc.interfaces.rest.resources.AddSolutionNoteResource;
import com.kipu.backend.rnc.interfaces.rest.resources.AssignNonConformityRecordResource;
import com.kipu.backend.rnc.interfaces.rest.resources.CreateNonConformityRecordResource;
import com.kipu.backend.rnc.interfaces.rest.resources.NonConformityRecordResource;
import com.kipu.backend.rnc.interfaces.rest.transform.CreateNonConformityRecordCommandFromResourceAssembler;
import com.kipu.backend.rnc.interfaces.rest.transform.NonConformityRecordResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for managing Non-Conformance Records (RNCs).
 */
@RestController
@RequestMapping("/api/v1/non-conformity-records")
@Tag(name = "Non-Conformity Records", description = "Endpoints for Non-Conformance Record Management")
public class NonConformityRecordsController {

    private final NonConformityRecordCommandService commandService;
    private final NonConformityRecordQueryService queryService;

    public NonConformityRecordsController(NonConformityRecordCommandService commandService, NonConformityRecordQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a new Non-Conformity Record")
    public ResponseEntity<NonConformityRecordResource> createNonConformityRecord(@Valid @RequestBody CreateNonConformityRecordResource resource) {
        var command = CreateNonConformityRecordCommandFromResourceAssembler.toCommandFromResource(resource);
        var rnc = commandService.handle(command);

        if (rnc.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var rncResource = NonConformityRecordResourceFromEntityAssembler.toResourceFromEntity(rnc.get());
        return new ResponseEntity<>(rncResource, HttpStatus.CREATED);
    }

    @GetMapping("/{rncId}")
    @Operation(summary = "Get a Non-Conformity Record by ID")
    public ResponseEntity<NonConformityRecordResource> getNonConformityRecordById(@PathVariable String rncId) {
        var query = new GetNonConformityRecordByIdQuery(rncId);
        var rnc = queryService.handle(query);

        if (rnc.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var rncResource = NonConformityRecordResourceFromEntityAssembler.toResourceFromEntity(rnc.get());
        return ResponseEntity.ok(rncResource);
    }

    @GetMapping
    @Operation(summary = "Get all Non-Conformity Records by Project ID")
    public ResponseEntity<List<NonConformityRecordResource>> getNonConformityRecordsByProjectId(@RequestParam String projectId) {
        var query = new GetAllNonConformityRecordsByProjectIdQuery(projectId);
        var rncs = queryService.handle(query);

        var resources = rncs.stream()
                .map(NonConformityRecordResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{rncId}/solution-notes")
    @Operation(summary = "Add a solution note to a Non-Conformity Record")
    public ResponseEntity<NonConformityRecordResource> addSolutionNote(
            @PathVariable String rncId,
            @Valid @RequestBody AddSolutionNoteResource resource) {
        var command = new AddSolutionNoteCommand(rncId, resource.note(), resource.authorId());
        var rnc = commandService.handle(command);

        if (rnc.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var rncResource = NonConformityRecordResourceFromEntityAssembler.toResourceFromEntity(rnc.get());
        return ResponseEntity.ok(rncResource);
    }

    @PutMapping("/{rncId}/assignee")
    @Operation(summary = "Assign a Non-Conformity Record to a user")
    public ResponseEntity<NonConformityRecordResource> assignNonConformityRecord(
            @PathVariable String rncId,
            @Valid @RequestBody AssignNonConformityRecordResource resource) {
        var command = new AssignNonConformityRecordCommand(rncId, resource.assigneeId());
        var rnc = commandService.handle(command);

        if (rnc.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var rncResource = NonConformityRecordResourceFromEntityAssembler.toResourceFromEntity(rnc.get());
        return ResponseEntity.ok(rncResource);
    }
}