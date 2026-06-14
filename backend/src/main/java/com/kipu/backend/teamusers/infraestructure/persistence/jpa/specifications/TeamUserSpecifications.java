package com.kipu.backend.teamusers.infraestructure.persistence.jpa.specifications;

import com.kipu.backend.teamusers.infraestructure.persistence.jpa.entities.TeamUserJpaEntity;
import org.springframework.data.jpa.domain.Specification;

public class TeamUserSpecifications {

    public static Specification<TeamUserJpaEntity> searchByTerm(String projectId, String term) {
        return ((root, query, builder) -> {

            var projectPredicate = builder.equal(root.get("projectId"), projectId);

            if (term == null || term.isEmpty()){
                return builder.conjunction();
            }



            String pattern = "%" + term.toLowerCase() + "%";

            var searchPredicate = builder.or(
                    builder.like(builder.lower(root.get("fullName")), pattern),
                    builder.like(builder.lower(root.get("email")), pattern),
                    builder.like(builder.lower(root.get("role")), pattern)
            );

            return builder.and(projectPredicate, searchPredicate);

            }
        );
    }
}
