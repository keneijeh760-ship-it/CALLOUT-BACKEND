package com.phope.realcalloutbackend.admin;
import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.admin.dto.AssignIncidentRequest;
import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.dto.IncidentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@PreAuthorize("hasRole('MODERATOR')")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/incidents")
    public ResponseEntity<ApiResponse<Page<IncidentResponse>>> getIncidentQueue(
            Pageable pageable) {

        Page<Incident> incidents = adminService.getIncidentQueue(pageable);

        // Map each Incident entity to IncidentResponse DTO
        Page<IncidentResponse> response = incidents.map(this::toResponse);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PatchMapping("/incidents/{id}/assign")
    public ResponseEntity<ApiResponse<IncidentResponse>> assignIncident(
            @PathVariable UUID id,
            @RequestBody @Valid AssignIncidentRequest request) {

        Incident incident = adminService.assignIncident(id, request);
        return ResponseEntity.ok(ApiResponse.ok(toResponse(incident)));
    }

    @GetMapping("/incidents/export")
    public ResponseEntity<String> exportCsv() {
        String csv = adminService.exportCsv(TenantContext.getTenant());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=incidents.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }

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