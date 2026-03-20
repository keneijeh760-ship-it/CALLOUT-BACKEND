package com.phope.realcalloutbackend.admin;

import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.IncidentRepository;
import com.phope.realcalloutbackend.incident.IncidentStatus;
import com.phope.realcalloutbackend.incident.dto.IncidentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final IncidentRepository incidentRepository;

    public Page<Incident> getIncidentQueue(Pageable pageable) {
        UUID orgId = TenantContext.getTenant();

        // Moderators see everything except spam
        return incidentRepository.findActiveFeed(
                orgId,
                List.of(IncidentStatus.SPAM),
                pageable
        );
    }
}
