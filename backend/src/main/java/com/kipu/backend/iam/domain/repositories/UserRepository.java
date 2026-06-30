package com.kipu.backend.iam.domain.repositories;

import com.kipu.backend.iam.domain.model.aggregates.User;
import java.util.Optional;
import java.util.List;

/**
 * Pure domain repository interface for User aggregate.
 * Free from framework dependency.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    boolean existsByEmail(String email);
}
