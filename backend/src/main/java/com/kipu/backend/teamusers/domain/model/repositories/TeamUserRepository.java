package com.kipu.backend.teamusers.domain.model.repositories;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface TeamUserRepository {

    TeamUser save(TeamUser teamUser);

    Optional<TeamUser> findById(String id);
    List<TeamUser> findByProjectId(String projectId);
    List<TeamUser> findByIsActiveTrue(String projectId);
    List<TeamUser> search(String projectId, String searchTerm);
    Optional<TeamUser> findByEmailAddressAndProjectId(EmailAddress email, String projectId);
}
