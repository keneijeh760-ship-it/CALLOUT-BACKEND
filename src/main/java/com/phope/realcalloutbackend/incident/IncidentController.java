package com.phope.realcalloutbackend.incident;

import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.incident.dto.IncidentResponse;
import com.phope.realcalloutbackend.incident.dto.SubmitIncidentRequest;
import com.phope.realcalloutbackend.incident.dto.UpdateStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping
    public ResponseEntity<ApiResponse<IncidentResponse>> submit(
            @RequestBody @Valid SubmitIncidentRequest request,
            @AuthenticationPrincipal Jwt jwt) {


        Incident incident = incidentService.submitIncident(request, jwt);


        IncidentResponse response = toResponse(incident);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<IncidentResponse>>> getFeed(Pageable pageable) {
        Page<Incident> incidents = incidentService.getFeed(pageable);


        Page<IncidentResponse> feed = incidents.map(this::toResponse);

        return ResponseEntity.ok(ApiResponse.ok(feed));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidentResponse>> getById(
            @PathVariable UUID id) {

        Incident incident = incidentService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(toResponse(incident)));
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('RESOLVER', 'MODERATOR')")
    public ResponseEntity<ApiResponse<IncidentResponse>> updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateStatusRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        Incident incident = incidentService.updateStatus(id, request, jwt);
        return ResponseEntity.ok(ApiResponse.ok(toResponse(incident)));
    }

    // Private helper — converts Incident entity to IncidentResponse DTO
    // Extracted as a method because three endpoints need the same mapping
    // rather than duplicating the builder code three times
    private IncidentResponse toResponse(Incident incident) {
        return IncidentResponse.builder()
                .id(incident.getId())
                .reporterId(incident.getReporterId())
                .title(incident.getTitle())
                .description(incident.getDescription())
                .categoryId(incident.getCategoryId())
                .locationTag(incident.getLocationTag())
                .status(incident.getIncidentStatus())
                .urgency(incident.getIncidentUrgency())
                .upvoteCount(incident.getUpvoteCount())
                .trackCount(incident.getTrackCount())
                .attachmentUrls(incident.getAttachment_urls())
                .createdAt(incident.getCreatedAt())
                .build();
    }
}