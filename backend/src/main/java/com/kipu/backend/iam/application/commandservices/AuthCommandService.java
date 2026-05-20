package com.kipu.backend.iam.application.commandservices;

import com.kipu.backend.iam.application.transform.AuthResource;
import com.kipu.backend.iam.application.transform.LoginCommand;

/**
 * Service defining authentication command handling contracts.
 */
public interface AuthCommandService {
    AuthResource handle(LoginCommand command);
}
