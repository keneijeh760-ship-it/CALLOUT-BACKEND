package com.phope.realcalloutbackend.incident;

import com.phope.realcalloutbackend.Shared.config.exception.InvalidStateTransitionException;
import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.incident.dto.SubmitIncidentRequest;
import com.phope.realcalloutbackend.incident.dto.UpdateStatusRequest;
import com.phope.realcalloutbackend.user.User;
import com.phope.realcalloutbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final UserService userService;
    @Transactional
    public Incident submitIncident(SubmitIncidentRequest request, Jwt jwt){
        User reporter = userService.getOrCreateUser(jwt);
        UUID  orgId = TenantContext.getTenant();

        Incident incident = new Incident();
        incident.setOrgId(orgId);
        incident.setReporterId(reporter.getId());
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setCategoryId(request.getCategoryId());
        incident.setLocationTag(request.getLocationTag());
        incident.setIncidentUrgency(request.getUrgency() != null
                ? request.getUrgency()
                : IncidentUrgency.NORMAL);
        incident.setAttachment_urls(request.getAttachmentUrls());

        return incidentRepository.save(incident);





    }

    public Incident getById (UUID id){
        UUID orgId = TenantContext.getTenant();

        Incident incident = incidentRepository.findByIdAndOrgId(id, orgId)
                .orElseThrow(() -> new NotFoundException("Incident", id));

        return  incident;
    }

    public Page<Incident> getFeed(Pageable pageable){
        UUID orgId = TenantContext.getTenant();

        return incidentRepository.findActiveFeed(
                orgId,
                List.of(IncidentStatus.RESOLVED,
                        IncidentStatus.ARCHIVED,
                        IncidentStatus.SPAM),
                pageable
        );
    }

    @Transactional
    public Incident updateStatus(UUID incidentId, UpdateStatusRequest request, Jwt jwt){

        Incident incident = getById(incidentId);


        IncidentStatus currentStatus = incident.getIncidentStatus();
        IncidentStatus newStatus = request.getNewStatus();

        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new InvalidStateTransitionException(
                    currentStatus.name(),
                    newStatus.name()
            );
        }

        //  apply the change
        incident.setIncidentStatus(newStatus);

        // set timestamps for terminal states
        if (newStatus == IncidentStatus.RESOLVED) {
            incident.setResolvedAt(Instant.now());
        }
        if (newStatus == IncidentStatus.ARCHIVED) {
            incident.setArchivedAt(Instant.now());
        }


        return incidentRepository.save(incident);

    }


}
