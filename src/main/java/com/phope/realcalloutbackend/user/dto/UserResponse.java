package com.phope.realcalloutbackend.user.dto;

import com.phope.realcalloutbackend.Shared.config.request.audit.AuditEntity;
import com.phope.realcalloutbackend.user.UserRole;
import lombok.*;

import java.time.Instant;
import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse  {
    private UUID id;
    private String username;
    private UserRole role;
    private Integer trustScore;
    private Instant createdAt;


}
