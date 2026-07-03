package com.kipu.backend.project.application.internal.commandservices;

import com.kipu.backend.project.application.commandservices.ProjectCommandService;
import com.kipu.backend.project.application.transform.CreateProjectCommand;
import com.kipu.backend.project.application.transform.CreateProjectItemCommand;
import com.kipu.backend.project.application.transform.UpdateProjectStatusCommand;
import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.model.entities.ProjectItem;
import com.kipu.backend.project.domain.model.exceptions.DuplicateProjectNameException;
import com.kipu.backend.project.domain.model.exceptions.ProjectNotFoundException;
import com.kipu.backend.project.domain.model.valueobjects.ProjectStatus;
import com.kipu.backend.project.domain.repositories.ProjectItemRepository;
import com.kipu.backend.project.domain.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for handling Project-related commands.
 */
@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final ProjectRepository projectRepository;
    private final ProjectItemRepository projectItemRepository;

    public ProjectCommandServiceImpl(ProjectRepository projectRepository,
                                     ProjectItemRepository projectItemRepository) {
        this.projectRepository = projectRepository;
        this.projectItemRepository = projectItemRepository;
    }

    @Override
    @Transactional
    public Project handle(CreateProjectCommand command, String username) {
        if (projectRepository.existsByName(command.name())) {
            throw new DuplicateProjectNameException(command.name());
        }

        Project project = new Project(
                command.name().trim(),
                command.description() != null ? command.description().trim() : "",
                command.startDate(),
                command.endDate(),
                command.totalBudget(),
                command.location().trim(),
                username,
                command.imageUrl()
        );

        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project handle(String id, UpdateProjectStatusCommand command) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        ProjectStatus status;
        try {
            status = ProjectStatus.valueOf(command.status().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("project.error.invalidStatus");
        }

        project.updateStatus(status, command.statusJustification());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public List<ProjectItem> handle(String projectId, List<CreateProjectItemCommand> commands) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        List<ProjectItem> itemsToSave = new ArrayList<>();
        for (CreateProjectItemCommand cmd : commands) {
            LocalDate start = parseLocalDate(cmd.startDate());
            LocalDate end = parseLocalDate(cmd.endDate());

            ProjectItem item = new ProjectItem(
                    cmd.name(),
                    start,
                    end,
                    project
            );
            itemsToSave.add(item);
        }

        return projectItemRepository.saveAll(itemsToSave);
    }

    /**
     * Helper method to parse ISO or simple date formats.
     */
    private LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Date string cannot be null or empty");
        }
        String cleanDate = dateStr.trim();
        if (cleanDate.contains("T")) {
            cleanDate = cleanDate.substring(0, cleanDate.indexOf("T"));
        }
        return LocalDate.parse(cleanDate);
    }

    @Override
    @Transactional
    public void handleDelete(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        
        projectItemRepository.deleteByProjectId(id);
        projectRepository.deleteById(id);
    }
}
