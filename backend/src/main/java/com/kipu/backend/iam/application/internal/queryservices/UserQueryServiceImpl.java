package com.kipu.backend.iam.application.internal.queryservices;

import com.kipu.backend.iam.application.queryservices.UserQueryService;
import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation for user queries utilizing domain repository.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    // Constructor injection
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> handle(Long id) {
        return userRepository.findById(id);
    }
}
