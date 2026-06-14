package com.kipu.backend.teamusers.interfaces.rest.controllers;


import com.kipu.backend.teamusers.application.commands.ActivateTeamUserCommand;
import com.kipu.backend.teamusers.application.commands.DeactiveTeamUserCommand;
import com.kipu.backend.teamusers.application.internal.commandservices.TeamUserCommandService;
import com.kipu.backend.teamusers.application.internal.queriesservices.TeamUserQueryService;
import com.kipu.backend.teamusers.application.queries.GetAllActiveTeamUsersQuery;
import com.kipu.backend.teamusers.application.queries.GetAllTeamUsersQuery;
import com.kipu.backend.teamusers.application.queries.SearchTeamUsersQuery;
import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.interfaces.rest.resources.CreateTeamUserResource;
import com.kipu.backend.teamusers.interfaces.rest.resources.TeamUserResource;
import com.kipu.backend.teamusers.interfaces.rest.transform.CreateTeamUserCommandFromResourceAssembler;
import com.kipu.backend.teamusers.interfaces.rest.transform.TeamUserResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team-users")
public class TeamUserController {

    private final TeamUserCommandService commandService;
    private final TeamUserQueryService queryService;

    public TeamUserController(TeamUserCommandService commandService, TeamUserQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<TeamUserResource> createTeamUser(@Valid @RequestBody CreateTeamUserResource resource) {
        var command = CreateTeamUserCommandFromResourceAssembler.toCommand(resource);
        var result = commandService.handle(command);

        return result.map(user ->
                        new ResponseEntity<>(TeamUserResourceFromEntityAssembler.toResource(user),
                HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<TeamUserResource>> getAllTeamUsers(
            @RequestParam String projectId,
            @RequestParam(required = false) String searchTerm) {
        if (searchTerm != null) {
            var query = new SearchTeamUsersQuery(projectId, searchTerm);
            return ResponseEntity.ok(queryService.handle(query)
                    .stream().map(TeamUserResourceFromEntityAssembler::toResource).toList());
        }
        var query = new GetAllTeamUsersQuery(projectId);
        return ResponseEntity.ok(queryService.handle(query)
                .stream().map(TeamUserResourceFromEntityAssembler::toResource).toList());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeamUserResource>> getAllActiveTeamUsers(
            @RequestParam String projectId
    ) {
        var query = new GetAllActiveTeamUsersQuery(projectId);
        return ResponseEntity.ok(queryService.handle(query)
                .stream().map(TeamUserResourceFromEntityAssembler::toResource).toList());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<TeamUserResource> deactivateTeamUser(
            @PathVariable String id
    ) {
        var command = new DeactiveTeamUserCommand(id);
        var result = commandService.handle(command);

        return result.map(user ->
                ResponseEntity.ok(TeamUserResourceFromEntityAssembler.toResource(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<TeamUserResource> activateTeamUser(
            @PathVariable String id
    ) {
        var command = new ActivateTeamUserCommand(id);
        var result = commandService.handle(command);

        return result.map(user ->
                        ResponseEntity.ok(TeamUserResourceFromEntityAssembler.toResource(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
