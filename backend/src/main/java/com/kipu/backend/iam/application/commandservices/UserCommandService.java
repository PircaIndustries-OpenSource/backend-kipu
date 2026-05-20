package com.kipu.backend.iam.application.commandservices;

import com.kipu.backend.iam.application.transform.RegisterUserCommand;
import com.kipu.backend.iam.application.transform.UpdateUserRoleCommand;
import com.kipu.backend.iam.domain.model.aggregates.User;

/**
 * Service defining user command handling contracts.
 */
public interface UserCommandService {
    User handle(RegisterUserCommand command);
    User handle(Long id, UpdateUserRoleCommand command);
}
