package com.phope.realcalloutbackend.admin;

import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.admin.dto.AssignIncidentRequest;
import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.dto.IncidentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@PreAuthorize("hasRole('MODERATOR')")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/incidents")
    public ResponseEntity<Page<Incident>> incidentQueue (Pageable pageable){

        Page<Incident> incident = adminService.getIncidentQueue(pageable);

        return ResponseEntity.ok(incident);

    }

    @GetMapping("/incidents/export")
    public ResponseEntity<String> exportCsv() {
        String csv = adminService.exportCsv(TenantContext.getTenant());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=incidents.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }

    @PatchMapping("/incidents/{id}/assign")
    public ResponseEntity<Incident> assignIncident (UUID incidentId, AssignIncidentRequest request){
        Incident incident = adminService.assignIncident(incidentId, request);

        return ResponseEntity.ok(incident);
    }

}
