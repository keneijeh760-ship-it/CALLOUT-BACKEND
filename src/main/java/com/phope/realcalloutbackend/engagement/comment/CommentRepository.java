package com.phope.realcalloutbackend.engagement.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByIncidentIdAndIsDeletedFalseOrderByCreatedAtAsc(UUID incidentId);
}