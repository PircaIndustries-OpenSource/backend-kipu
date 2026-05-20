package com.kipu.backend.iam.application.queryservices;

import com.kipu.backend.iam.domain.model.aggregates.User;
import java.util.Optional;

/**
 * Service defining user query handling contracts.
 */
public interface UserQueryService {
    Optional<User> handle(Long id);
}
