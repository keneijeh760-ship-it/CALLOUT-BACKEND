package com.phope.realcalloutbackend.engagement.comment;

import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.engagement.comment.dto.CommentResponse;
import com.phope.realcalloutbackend.engagement.comment.dto.CreateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/incidents/{incidentId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // GET /incidents/{incidentId}/comments — public
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable UUID incidentId) {

        List<Comment> comments = commentService.getCommentsByIncident(incidentId);

        List<CommentResponse> response = comments.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // POST /incidents/{incidentId}/comments — authenticated
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable UUID incidentId,
            @RequestBody @Valid CreateCommentRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        Comment comment = commentService.addComment(incidentId, request, jwt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(toResponse(comment)));
    }

    // DELETE /incidents/{incidentId}/comments/{id} — authenticated, own comments only
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable UUID incidentId,
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {

        commentService.deleteComment(id, jwt);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    // PATCH /incidents/{incidentId}/comments/{id}/pin — MODERATOR only
    @PatchMapping("/{id}/pin")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<CommentResponse>> pinComment(
            @PathVariable UUID incidentId,
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {

        Comment comment = commentService.pinComment(id, jwt);
        return ResponseEntity.ok(ApiResponse.ok(toResponse(comment)));
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .incidentId(comment.getIncidentId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .isPinned(comment.isPinned())
                .pinnedBy(comment.getPinnedBy())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}