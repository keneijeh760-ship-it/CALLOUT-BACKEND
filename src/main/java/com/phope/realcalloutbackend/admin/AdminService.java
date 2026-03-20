package com.phope.realcalloutbackend.admin;

import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.Shared.config.tenant.TenantContext;
import com.phope.realcalloutbackend.admin.dto.AssignIncidentRequest;
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
@Transactional(readOnly = true)
public class AdminService {

    private final IncidentRepository incidentRepository;
    UUID orgId = TenantContext.getTenant();

    public Page<Incident> getIncidentQueue(Pageable pageable) {


        // Moderators see everything except spam
        return incidentRepository.findActiveFeed(
                orgId,
                List.of(IncidentStatus.SPAM),
                pageable
        );
    }

    public Incident assignIncident(UUID incidentId, AssignIncidentRequest request){
        Incident incident = incidentRepository.findByIdAndOrgId(incidentId, orgId )
                .orElseThrow(() -> new NotFoundException("Incident", incidentId));

        incident.setAssignedTo(request.getResolverId());

        return incidentRepository.save(incident);
    }

    public String exportCsv(UUID orgId){
        List<Incident> incidents = incidentRepository.findAllByOrgIdAndStatusNot(orgId, IncidentStatus.SPAM);
        StringBuilder csv = new StringBuilder();
        csv.append("id,title,status,urgency,createdAt\n");
        incidents.forEach(i -> csv.append(String.format("%s,%s,%s,%s,%s\n",
                i.getId(), i.getTitle(), i.getIncidentStatus(),
                i.getIncidentUrgency(), i.getCreatedAt())));
        return csv.toString();
    }
}
