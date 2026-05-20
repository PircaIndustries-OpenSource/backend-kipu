package com.kipu.backend.iam.application.internal.commandservices;

import com.kipu.backend.iam.application.commandservices.AuthCommandService;
import com.kipu.backend.iam.application.transform.AuthResource;
import com.kipu.backend.iam.application.transform.LoginCommand;
import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.model.exceptions.UserNotFoundException;
import com.kipu.backend.iam.domain.repositories.UserRepository;
import com.kipu.backend.iam.infrastructure.security.token.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service implementation for authentication commands executing token issuance.
 */
@Service
public class AuthCommandServiceImpl implements AuthCommandService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // Constructor injection
    public AuthCommandServiceImpl(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResource handle(LoginCommand command) {
        // Authenticate credentials using Spring Security AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );

        // Retrieve user details from repository for token resource context
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new UserNotFoundException(command.email()));

        // Generate the secure JWT token
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Return token and basic identity profile
        return new AuthResource(jwt, user.getId(), user.getEmail(), user.getRole().name());
    }
}
