package com.kipu.backend.teamworkers.interfaces.rest.controllers;

import com.kipu.backend.teamworkers.application.commands.AssignMachineryToTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.DeleteTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.RemoveMachineryFromTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.internal.commandservices.TeamWorkerCommandService;
import com.kipu.backend.teamworkers.application.internal.queriesservices.TeamWorkerQueryService;
import com.kipu.backend.teamworkers.application.queries.GetAllTeamWorkersByProjectIdQuery;
import com.kipu.backend.teamworkers.application.queries.GetTeamWorkerByIdQuery;
import com.kipu.backend.teamworkers.interfaces.rest.resources.AssignMachineryResource;
import com.kipu.backend.teamworkers.interfaces.rest.resources.CreateTeamWorkerResource;
import com.kipu.backend.teamworkers.interfaces.rest.resources.TeamWorkerResource;
import com.kipu.backend.teamworkers.interfaces.rest.transform.CreateTeamWorkerCommandFromResourceAssembler;
import com.kipu.backend.teamworkers.interfaces.rest.transform.TeamWorkerResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team-workers")
public class TeamWorkersController {

    private final TeamWorkerCommandService commandService;
    private final TeamWorkerQueryService queryService;

    public TeamWorkersController(TeamWorkerCommandService commandService, TeamWorkerQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<TeamWorkerResource> createTeamWorker(@RequestBody CreateTeamWorkerResource resource) {
        var command = CreateTeamWorkerCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();

        var responseResource = TeamWorkerResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return new ResponseEntity<>(responseResource, HttpStatus.CREATED); // Equivale a CreatedAtAction
    }

    @DeleteMapping("/{teamWorkerId}")
    public ResponseEntity<Void> deleteTeamWorker(@PathVariable String teamWorkerId) {
        var command = new DeleteTeamWorkerCommand(teamWorkerId);
        var success = commandService.handle(command);

        if (!success) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/{teamWorkerId}/machineries")
    public ResponseEntity<TeamWorkerResource> assignMachinery(
            @PathVariable String teamWorkerId,
            @RequestBody AssignMachineryResource resource) {

        var command = new AssignMachineryToTeamWorkerCommand(teamWorkerId, resource.machineryId(), resource.fullName());
        var result = commandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();

        var responseResource = TeamWorkerResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(responseResource);
    }

    @DeleteMapping("/{teamWorkerId}/machineries/{machineryId}")
    public ResponseEntity<TeamWorkerResource> removeMachinery(
            @PathVariable String teamWorkerId,
            @PathVariable String machineryId) {

        var command = new RemoveMachineryFromTeamWorkerCommand(teamWorkerId, machineryId);
        var result = commandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();

        var responseResource = TeamWorkerResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(responseResource);
    }

    @GetMapping("/{teamWorkerId}")
    public ResponseEntity<TeamWorkerResource> getTeamWorkerById(@PathVariable String teamWorkerId) {
        var query = new GetTeamWorkerByIdQuery(teamWorkerId);
        var result = queryService.handle(query);

        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var resource = TeamWorkerResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<List<TeamWorkerResource>> getTeamWorkers(
            @RequestParam String projectId,
            @RequestParam(required = false) String globalSearch) {

        var query = new GetAllTeamWorkersByProjectIdQuery(projectId, globalSearch);
        var results = queryService.handle(query);

        var resources = results.stream()
                .map(TeamWorkerResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}