package com.phope.realcalloutbackend.duplicate;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DuplicateSuggestionRepository extends JpaRepository<DuplicateSuggestion, UUID> {

    List<DuplicateSuggestion> findAllBySourceIncidentIdAndIsResolvedFalse(
            UUID sourceIncidentId);
}