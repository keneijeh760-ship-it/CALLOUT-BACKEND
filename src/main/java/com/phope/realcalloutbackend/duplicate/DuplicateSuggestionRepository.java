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




}
