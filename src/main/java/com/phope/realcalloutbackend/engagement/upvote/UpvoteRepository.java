package com.phope.realcalloutbackend.engagement.upvote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UpvoteRepository extends JpaRepository<Upvote, UUID> {

    Optional<Upvote> findByIncidentIdAndUserId(UUID incidentId, UUID userId);

    void deleteByIncidentIdAndUserId(UUID incidentId, UUID userId);

    boolean existsUpvoteByUserId(UUID userId);
}
