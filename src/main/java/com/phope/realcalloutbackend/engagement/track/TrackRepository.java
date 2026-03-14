package com.phope.realcalloutbackend.engagement.track;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {

    boolean existsByIncidentIdAndUserId(UUID incidentId, UUID userId);

    Optional<Track> findByIncidentIdAndUserId(UUID incidentId, UUID userId);
}