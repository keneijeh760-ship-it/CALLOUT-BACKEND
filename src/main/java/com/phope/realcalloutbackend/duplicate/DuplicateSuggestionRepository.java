package com.phope.realcalloutbackend.duplicate;

import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DuplicateSuggestionRepository extends JpaRepository<UUID, DuplicateSuggestion> {
    Optional<DuplicateSuggestion> findAllBySourceIncidentIdAndIsResolvedFalse(UUID sourceIncidentId);

    @Query(value = """
    SELECT i FROM Incident i
    WHERE i.orgId = :orgId
    AND i.id != :incidentId
    AND i.incidentStatus NOT IN :excludedStatuses
    AND function('similarity', i.title, :title) > 0.4
    ORDER BY function('similarity', i.title, :title) DESC
    """)
    List<Incident> findSimilarByTitle(
            @Param("incidentId") UUID incidentId,
            @Param("title") String title,
            @Param("orgId") UUID orgId,
            @Param("excludedStatuses") Collection<IncidentStatus> excludedStatuses
    );


}
