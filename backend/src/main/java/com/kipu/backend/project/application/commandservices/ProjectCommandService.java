package com.kipu.backend.project.application.commandservices;

import com.kipu.backend.project.application.transform.CreateProjectCommand;
import com.kipu.backend.project.application.transform.CreateProjectItemCommand;
import com.kipu.backend.project.application.transform.UpdateProjectStatusCommand;
import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.model.entities.ProjectItem;

import java.util.List;

/**
 * Service defining project command handling contracts.
 */
public interface ProjectCommandService {
    Project handle(CreateProjectCommand command, String username);
    Project handle(String id, UpdateProjectStatusCommand command);
    List<ProjectItem> handle(String projectId, List<CreateProjectItemCommand> commands);
    void handleDelete(String id);
}
