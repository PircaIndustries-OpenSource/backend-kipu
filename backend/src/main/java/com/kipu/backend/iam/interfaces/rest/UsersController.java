package com.kipu.backend.iam.interfaces.rest;

import com.kipu.backend.iam.application.commandservices.UserCommandService;
import com.kipu.backend.iam.application.queryservices.UserQueryService;
import com.kipu.backend.iam.application.transform.RegisterUserCommand;
import com.kipu.backend.iam.application.transform.UpdateUserRoleCommand;
import com.kipu.backend.iam.application.transform.UserResource;
import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.model.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling user management endpoints.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for user management")
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    // Constructor injection
    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Creates a new user record.
     */
    @PostMapping
    @Operation(summary = "Create a new user registration profile")
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody RegisterUserCommand command) {
        User user = userCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResource.fromUser(user));
    }

    /**
     * Retrieves user profile details by identifier. Requires token authorization.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user profile details by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        User user = userQueryService.handle(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(UserResource.fromUser(user));
    }

    @GetMapping
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserResource>> getAllUsers() {
        List<User> users = userQueryService.handleGetAll();
        List<UserResource> resources = users.stream()
                .map(UserResource::fromUser)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    /**
     * Updates an existing user role, restricted exclusively to ADMIN users.
     */
    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role (restricted to ADMIN only)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResource> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleCommand command) {
        User updatedUser = userCommandService.handle(id, command);
        return ResponseEntity.ok(UserResource.fromUser(updatedUser));
    }
}
