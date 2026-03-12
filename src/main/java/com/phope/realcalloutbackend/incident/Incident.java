package com.phope.realcalloutbackend.incident;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "incident")
@Getter
@Setter
@NoArgsConstructor
public class Incident extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;
    @Column(name = "org_id", nullable = false)
    private UUID orgId;
    @Column(name = "reporter_id", nullable = false)
    private UUID reporterId;
    @Column(name = "assigned_to")
    private UUID assignedTo;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "location_tag")
    private String locationTag;
    @Column(name = "attachment_urls")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> attachment_urls = new ArrayList<>();
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private IncidentStatus incidentStatus = IncidentStatus.PENDING;
    @Column(name = "urgency", nullable = false)
    @Enumerated(EnumType.STRING)
    private IncidentUrgency incidentUrgency = IncidentUrgency.NORMAL;
    @Column(name = "is_urgent_flagged", nullable = false)
    private boolean isUrgentFlagged;
    @Column(name = "upvote_count", nullable = false)
    private int upvoteCount = 0;
    @Column(name = "track_count", nullable = false)
    private int trackCount = 0;
    @Column(name = "resolved_at")
    private Instant resolvedAt;
    @Column(name = "archived_at")
    private Instant archivedAt;













}
