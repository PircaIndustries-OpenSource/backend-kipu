package com.kipu.backend.iam.application.internal.commandservices;

import com.kipu.backend.iam.application.commandservices.UserCommandService;
import com.kipu.backend.iam.application.transform.RegisterUserCommand;
import com.kipu.backend.iam.application.transform.UpdateUserRoleCommand;
import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.model.exceptions.EmailAlreadyExistsException;
import com.kipu.backend.iam.domain.model.exceptions.UserNotFoundException;
import com.kipu.backend.iam.domain.model.valueobjects.Roles;
import com.kipu.backend.iam.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Service implementation for user commands utilizing domain repository and transactional boundaries.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserCommandServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User handle(RegisterUserCommand command) {
        // Validate unique email constraint
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }

        // Validate and map role string to Roles enum
        Roles roleEnum = parseRole(command.role());

        // Secure password with BCrypt
        String encodedPassword = passwordEncoder.encode(command.password());

        // Create new User aggregate
        User user = new User(
                command.username().trim(),
                command.email().trim(),
                encodedPassword,
                roleEnum
        );

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User handle(Long id, UpdateUserRoleCommand command) {
        // Retrieve existing User
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Validate and map new role
        Roles roleEnum = parseRole(command.role());

        // Business logic execution on the User aggregate root
        user.updateRole(roleEnum);

        return userRepository.save(user);
    }

    /**
     * Helper method to validate and parse role strings.
     * Supports both raw name (e.g. "ADMIN") and security-prefixed names (e.g. "ROLE_ADMIN").
     */
    private Roles parseRole(String roleStr) {
        if (roleStr == null || roleStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be blank. Allowed roles: " + Arrays.toString(Roles.values()));
        }

        String normalized = roleStr.trim().toUpperCase();
        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }

        try {
            return Roles.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    String.format("Invalid role '%s'. Valid values are: %s", roleStr, Arrays.toString(Roles.values()))
            );
        }
    }
}
