package com.phope.realcalloutbackend.incident;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    @Query("""
    SELECT i FROM Incident i
    WHERE i.orgId = :orgId
    AND i.incidentStatus NOT IN :excludedStatuses
    ORDER BY i.incidentUrgency DESC, i.upvoteCount DESC, i.createdAt DESC
    """)
    Page<Incident> findActiveFeed(@Param("orgId") UUID orgId, Pageable pageable);

    Optional<Incident> findByIdAndOrgId(UUID id, UUID orgId);

}
