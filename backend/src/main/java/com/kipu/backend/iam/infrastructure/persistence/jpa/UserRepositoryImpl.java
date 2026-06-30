package com.kipu.backend.iam.infrastructure.persistence.jpa;

import com.kipu.backend.iam.domain.model.aggregates.User;
import com.kipu.backend.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.List;

/**
 * Infrastructure repository adapter implementing the pure UserRepository domain interface.
 * Decouples domain from direct Spring Data dependency.
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA userRepositoryJPA;

    // Constructor injection
    public UserRepositoryImpl(UserRepositoryJPA userRepositoryJPA) {
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public User save(User user) {
        return userRepositoryJPA.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryJPA.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryJPA.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryJPA.existsByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepositoryJPA.findAll();
    }
}
