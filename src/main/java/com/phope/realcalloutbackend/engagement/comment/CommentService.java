package com.phope.realcalloutbackend.engagement.comment;


import com.phope.realcalloutbackend.Shared.config.exception.ForbiddenException;
import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.engagement.comment.dto.CreateCommentRequest;
import com.phope.realcalloutbackend.user.User;
import com.phope.realcalloutbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public Comment addComment(UUID incidentId, CreateCommentRequest request, Jwt jwt) {
        User user = userService.getOrCreateUser(jwt);

        Comment comment = new Comment();
        comment.setIncidentId(incidentId);
        comment.setAuthorId(user.getId());
        comment.setContent(request.getContent());

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId, Jwt jwt) {
        User user = userService.getOrCreateUser(jwt);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment", commentId));

        // Ownership check — you can only delete your own comments
        if (!comment.getAuthorId().equals(user.getId())) {
            throw new ForbiddenException("You can only delete your own comments");
        }

        // Soft delete — never hard delete user content
        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    @Transactional
    public Comment pinComment(UUID commentId, Jwt jwt) {
        User user = userService.getOrCreateUser(jwt);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment", commentId));

        // All three fields set atomically — they are semantically linked
        comment.setPinned(true);
        comment.setPinnedBy(user.getId());
        comment.setPinnedAt(Instant.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByIncident(UUID incidentId) {
        return commentRepository
                .findAllByIncidentIdAndIsDeletedFalseOrderByCreatedAtAsc(incidentId);
    }
}