package com.phope.realcalloutbackend.engagement.upvote;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "upvotes")
public class Upvote  extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "incident_id", nullable = false)
    private UUID incidentId;
    @Column (name = "user_id", nullable = false)
    private UUID userId;
}
