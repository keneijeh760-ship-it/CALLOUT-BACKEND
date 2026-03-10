package com.phope.realcalloutbackend.Organization;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizations")
public class Organization extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "Domain", nullable = false, unique = true)
    private String domain;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(nullable = false)
    private boolean isActive = true;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "settings", columnDefinition = "jsonb")
    private Map<String, Object> settings = new HashMap<>();











}
