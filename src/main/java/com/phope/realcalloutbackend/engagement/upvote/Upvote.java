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
@Table(name = "upvote")
public class Upvote  extends AuditEntity {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "incident_Id", nullable = false)
    private UUID incidentId;
    @Column (name = "user_Id", nullable = false)
    private UUID userId;
}
