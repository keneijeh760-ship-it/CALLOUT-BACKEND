package com.phope.realcalloutbackend.user;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User  extends AuditEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;
    @Column(name = "org_id", nullable = false)
    private UUID orgId;
    @Column(name = "supabase_uid", unique = true, nullable = false)
    private String supabaseUid;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.REPORTER;
    @Column(name = "trust_score", nullable = false)
    private Integer trustSore = 100;
    @Column(name = "flag_count", nullable = false)
    private Integer flagCount = 0;
    @Column(name = "is_suspended", nullable = false)
    private Boolean isSuspended = false;
    @Column(name = "isBanned", nullable = false)
    private Boolean isBanned = false;
    @Column(name = "last_updated_at", nullable = false)
    private Instant lastUpdatdAt;




}
