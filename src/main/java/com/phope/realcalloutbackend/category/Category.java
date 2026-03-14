package com.phope.realcalloutbackend.category;

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
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "org_id", nullable = false)
    private UUID orgId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "slug", nullable = false)
    private String slug;
    @Column(name = "description")
    private String description;
    @Column(name = "is_active",  nullable = false)
    private boolean isActive = true;
}
