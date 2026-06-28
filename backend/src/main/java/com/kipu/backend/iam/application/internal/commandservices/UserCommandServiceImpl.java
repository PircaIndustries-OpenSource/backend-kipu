package com.kipu.backend.iam.application.internal.commandservices;

import com.kipu.backend.iam.application.commandservices.UserCommandService;
import com.kipu.backend.iam.application.transform.RegisterUserCommand;
import com.kipu.backend.iam.application.transform.UpdateUserRoleCommand;
import com.kipu.backend.iam.application.transform.UpdateUserPasswordCommand;
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
    private final com.kipu.backend.iam.application.internal.outboundservices.email.EmailService emailService;

    // Constructor injection
    public UserCommandServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, com.kipu.backend.iam.application.internal.outboundservices.email.EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

    @Override
    @Transactional
    public User handle(Long id, UpdateUserPasswordCommand command) {
        // Retrieve existing User
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Secure password with BCrypt
        String encodedPassword = passwordEncoder.encode(command.password());

        // Business logic execution on the User aggregate root
        user.updatePassword(encodedPassword);

        User savedUser = userRepository.save(user);

        // Send confirmation email
        String subject = "Kipu - Contraseña Actualizada Exitosamente";
        String body = "<h1>Contraseña Actualizada</h1>"
                    + "<p>Hola " + user.getUsername() + ",</p>"
                    + "<p>Te informamos que tu contraseña ha sido modificada con éxito. Si no fuiste tú, por favor contacta a soporte inmediatamente.</p>";
        try {
            this.emailService.sendHtmlEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedUser;
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
