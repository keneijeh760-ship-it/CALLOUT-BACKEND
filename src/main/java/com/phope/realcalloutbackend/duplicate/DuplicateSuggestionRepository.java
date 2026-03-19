package com.phope.realcalloutbackend.duplicate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface DuplicateSuggestionRepository extends JpaRepository<UUID, DuplicateSuggestion> {
    Optional<DuplicateSuggestion> findAllBySourceIncidentIdAndIsResolvedFalse(UUID sourceIncidentId);


}
