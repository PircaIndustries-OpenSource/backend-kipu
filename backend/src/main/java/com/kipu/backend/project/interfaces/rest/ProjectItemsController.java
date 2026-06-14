package com.kipu.backend.project.interfaces.rest;

import com.kipu.backend.project.application.commandservices.ProjectCommandService;
import com.kipu.backend.project.application.queryservices.ProjectQueryService;
import com.kipu.backend.project.application.transform.CreateProjectItemCommand;
import com.kipu.backend.project.application.transform.ProjectItemResource;
import com.kipu.backend.project.domain.model.entities.ProjectItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling project work items (Partidas) (TS10, TS11).
 */
@RestController
@RequestMapping("/api/v1/projects/{id}/items")
@Tag(name = "Project Items", description = "Endpoints for project work item/partida catalog management")
@SecurityRequirement(name = "bearerAuth")
public class ProjectItemsController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectItemsController(ProjectCommandService projectCommandService,
                                  ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    /**
     * Registers a list of partidas (TS10).
     */
    @PostMapping
    @Operation(summary = "Register project items (Partidas) to a project catalog")
    public ResponseEntity<List<ProjectItemResource>> registerProjectItems(
            @PathVariable("id") String projectId,
            @Valid @RequestBody List<CreateProjectItemCommand> commands) {
            
        List<ProjectItem> items = projectCommandService.handle(projectId, commands);
        
        List<ProjectItemResource> resources = items.stream()
                .map(ProjectItemResource::fromProjectItem)
                .collect(Collectors.toList());
                
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    /**
     * Retrieves the list of partidas for a project (TS11).
     */
    @GetMapping
    @Operation(summary = "Get list of items (Partidas) for a project")
    public ResponseEntity<List<ProjectItemResource>> getProjectItems(@PathVariable("id") String projectId) {
        List<ProjectItem> items = projectQueryService.handleGetProjectItemsByProjectId(projectId);
        
        List<ProjectItemResource> resources = items.stream()
                .map(ProjectItemResource::fromProjectItem)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(resources);
    }
}
