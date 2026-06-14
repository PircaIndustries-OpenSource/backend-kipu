package com.kipu.backend.documents.interfaces.controllers;

import com.kipu.backend.documents.application.commands.SignDocumentCommand;
import com.kipu.backend.documents.application.internal.commandservices.DocumentCommandService;
import com.kipu.backend.documents.application.internal.queriesservices.DocumentQueryService;
import com.kipu.backend.documents.application.queries.GetAllDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllPendingDocumentsQuery;
import com.kipu.backend.documents.application.queries.GetAllSignedDocumentsQuery;
import com.kipu.backend.documents.interfaces.resources.CreateDocumentResource;
import com.kipu.backend.documents.interfaces.resources.DocumentResource;
import com.kipu.backend.documents.interfaces.transform.CreateDocumentCommandFromResourceAssembler;
import com.kipu.backend.documents.interfaces.transform.DocumentResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentCommandService commandService;
    private final DocumentQueryService queryService;

    public DocumentController(DocumentCommandService commandService, DocumentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<DocumentResource> createDocument(@RequestBody CreateDocumentResource resource) {
        var command = CreateDocumentCommandFromResourceAssembler.toCommand(resource);
        var result = commandService.handle(command);

        return result.map(d -> new ResponseEntity<>(
                DocumentResourceFromEntityAssembler.toResource(d),
                HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/sign/{id}")
    public ResponseEntity<DocumentResource> signDocument(@PathVariable String id) {
        var command = new SignDocumentCommand(id);
        var result = commandService.handle(command);

        return result.map(d -> new ResponseEntity<>(
                DocumentResourceFromEntityAssembler.toResource(d),
                HttpStatus.ACCEPTED))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DocumentResource>> getAllDocumentsByProject(
            @RequestParam String projectId) {
        var query = new GetAllDocumentsQuery(projectId);
        return ResponseEntity.ok(queryService.handle(query).stream().map(
                DocumentResourceFromEntityAssembler::toResource
        ).toList());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<DocumentResource>> getAllPendingDocuments(@RequestParam String projectId) {
        var query = new GetAllPendingDocumentsQuery(projectId);
        return ResponseEntity.ok(queryService.handle(query).stream().map(
                DocumentResourceFromEntityAssembler::toResource
        ).toList());
    }

    @GetMapping("/signed")
    public ResponseEntity<List<DocumentResource>> getAllSignedDocuments(@RequestParam String projectId) {
        var query = new GetAllSignedDocumentsQuery(projectId);
        return ResponseEntity.ok(queryService.handle(query).stream().map(
                DocumentResourceFromEntityAssembler::toResource
        ).toList());
    }
}
