package com.phope.realcalloutbackend.engagement.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private UUID id;
    private UUID incidentId;
    private UUID authorId;
    private String content;
    private boolean isPinned;
    private UUID pinnedBy;
    private Instant createdAt;
}