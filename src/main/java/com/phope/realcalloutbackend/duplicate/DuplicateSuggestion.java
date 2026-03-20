package com.phope.realcalloutbackend.duplicate;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "duplicate_suggestions")
public class DuplicateSuggestion extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false, name = "id")
    private UUID id;
    @Column(name = "sourceIncidentId", nullable = false)
    private UUID sourceIncidentId;
    @Column(name = "suggestedIncidentId", nullable = false)
    private UUID suggestedIncidentId;
    @Column(name = "similiarity_score", nullable = false)
    private float similiarityScore;
    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    private Algorithm algorithm;
    @Column(name = "is_resolved", nullable = false)
    private boolean isResolved = false;


}
