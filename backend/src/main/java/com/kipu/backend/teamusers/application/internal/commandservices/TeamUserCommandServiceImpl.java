package com.kipu.backend.teamusers.application.internal.commandservices;

import com.kipu.backend.teamusers.application.commands.ActivateTeamUserCommand;
import com.kipu.backend.teamusers.application.commands.CreateTeamUserCommand;
import com.kipu.backend.teamusers.application.commands.DeactiveTeamUserCommand;
import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.exceptions.ExistingUserException;
import com.kipu.backend.teamusers.domain.model.repositories.TeamUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamUserCommandServiceImpl implements TeamUserCommandService {

    private final TeamUserRepository teamUserRepository;

    public TeamUserCommandServiceImpl(TeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }

    @Override
    public Optional<TeamUser> handle(CreateTeamUserCommand command) {

        var existingUser = teamUserRepository.findByEmailAddressAndProjectId(command.emailAddress(), command.projectId());

        if (existingUser.isPresent()) {
            throw new ExistingUserException();
        }

        var teamUser = new TeamUser(
                command.fullName(),
                command.emailAddress(),
                command.role(),
                command.projectId()
        );

        teamUser.setId("us-" + System.currentTimeMillis());

        try {
            var savedUser = teamUserRepository.save(teamUser);
            return Optional.of(savedUser);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<TeamUser> handle(DeactiveTeamUserCommand command) {
        var teamUser = teamUserRepository.findById(command.id());

        if (teamUser.isEmpty())return Optional.empty();

        var newUser = teamUser.get();
        newUser.deactivate();

        System.out.println("DEBUG: The user " + newUser.getId() + " has isActive = " + newUser.isActive());

        return Optional.of(teamUserRepository.save(newUser));
    }

    @Override
    @Transactional
    public Optional<TeamUser> handle(ActivateTeamUserCommand command) {
        var teamUser = teamUserRepository.findById(command.id());

        if (teamUser.isEmpty())return Optional.empty();

        var newUser = teamUser.get();
        newUser.activate();

        System.out.println("DEBUG: The user " + newUser.getId() + " has isActive = " + newUser.isActive());

        return Optional.of(teamUserRepository.save(newUser));
    }
}
