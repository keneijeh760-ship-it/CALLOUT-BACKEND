package com.phope.realcalloutbackend.incident;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    @Query("""
    SELECT i FROM Incident i
    WHERE i.orgId = :orgId
    AND i.incidentStatus NOT IN :excludedStatuses
    ORDER BY i.incidentUrgency DESC, i.upvoteCount DESC, i.createdAt DESC
    """)
    Page<Incident> findActiveFeed(@Param("orgId") UUID orgId,  @Param("excludedStatuses") Collection<IncidentStatus> excludedStatuses, Pageable pageable);

    Optional<Incident> findByIdAndOrgId(UUID id, UUID orgId);

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
