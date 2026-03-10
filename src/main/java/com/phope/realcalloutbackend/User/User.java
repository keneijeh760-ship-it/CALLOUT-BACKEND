package com.phope.realcalloutbackend.User;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "User")
public class User  extends AuditEntity {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "org_id", nullable = false)
    private UUID category_id;
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
    private Boolean isSuspended = Boolean.FALSE;
    @Column(name = "isBanned", nullable = false)
    private Boolean isBanned = Boolean.FALSE;
    @Column(name = "last_updated_at", nullable = false)
    private Timestamp lastUpdatdAt;




}
