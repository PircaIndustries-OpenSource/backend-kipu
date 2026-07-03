package com.kipu.backend.project.interfaces.rest;

import com.kipu.backend.project.application.commandservices.ProjectCommandService;
import com.kipu.backend.project.application.queryservices.ProjectQueryService;
import com.kipu.backend.project.application.transform.CreateProjectCommand;
import com.kipu.backend.project.application.transform.ProjectResource;
import com.kipu.backend.project.application.transform.UpdateProjectStatusCommand;
import com.kipu.backend.project.domain.model.aggregates.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling Construction Project endpoints.
 */
@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Projects", description = "Endpoints for project lifecycle management")
@SecurityRequirement(name = "bearerAuth")
public class ProjectsController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectsController(ProjectCommandService projectCommandService,
                              ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    /**
     * Creates/Registers a new project (TS07).
     */
    @PostMapping
    @Operation(summary = "Register a new project")
    public ResponseEntity<ProjectResource> createProject(@Valid @RequestBody CreateProjectCommand command) {
        String username = command.createdBy();
        if (username == null || username.isBlank()) {
            try {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            } catch (Exception e) {
                username = "anonymousUser";
            }
        }
        Project project = projectCommandService.handle(command, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectResource.fromProject(project));
    }

    /**
     * Lists all projects associated with the authenticated user (TS08).
     * Returns a direct JSON Array to align with Angular frontend expectations.
     */
    @GetMapping
    @Operation(summary = "Get all projects associated with the authenticated user")
    public ResponseEntity<List<ProjectResource>> getProjects(@RequestParam(value = "createdBy", required = false) String createdBy) {
        String username = createdBy;
        if (username == null || username.isBlank()) {
            try {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            } catch (Exception e) {
                username = "anonymousUser";
            }
        }
        List<Project> projects = projectQueryService.handleGetProjectsByCreatedBy(username);
        
        List<ProjectResource> resources = projects.stream()
                .map(ProjectResource::fromProject)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves a single project by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get project details by ID")
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable("id") String id) {
        Project project = projectQueryService.handleGetProjectById(id)
                .orElseThrow(() -> new com.kipu.backend.project.domain.model.exceptions.ProjectNotFoundException(id));
        return ResponseEntity.ok(ProjectResource.fromProject(project));
    }

    /**
     * Updates the status of a project (TS09 - Gherkin specification mapping).
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update project status (Gherkin mapping)")
    public ResponseEntity<ProjectResource> updateProjectStatusGherkin(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateProjectStatusCommand command) {
        Project project = projectCommandService.handle(id, command);
        return ResponseEntity.ok(ProjectResource.fromProject(project));
    }

    /**
     * Updates the status of a project (Angular frontend mapping).
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Update project status (Frontend mapping)")
    public ResponseEntity<ProjectResource> updateProjectStatusFrontend(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateProjectStatusCommand command) {
        Project project = projectCommandService.handle(id, command);
        return ResponseEntity.ok(ProjectResource.fromProject(project));
    }

    /**
     * Deletes a project by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project by ID")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String id) {
        projectCommandService.handleDelete(id);
        return ResponseEntity.noContent().build();
    }
}
