package com.phope.realcalloutbackend.engagement.comment;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment extends AuditEntity {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "incident_id", nullable = false)
    private UUID incidentId;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_pinned", nullable = false)
    private boolean isPinned = false;

    // Only populated when isPinned = true
    @Column(name = "pinned_by")
    private UUID pinnedBy;

    @Column(name = "pinned_at")
    private Instant pinnedAt;

    // Soft delete — row stays in DB, hidden from display
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}