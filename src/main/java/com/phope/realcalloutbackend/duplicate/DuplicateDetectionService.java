package com.phope.realcalloutbackend.duplicate;

import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.IncidentRepository;
import com.phope.realcalloutbackend.incident.IncidentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuplicateDetectionService {

    private final DuplicateSuggestionRepository duplicateSuggestionRepository;
    private final IncidentRepository incidentRepository;

    public List<Incident> findPotentialDuplicates(UUID incidentId, String title, UUID orgId){
        List<Incident> similarIncidents = incidentRepository.findSimilarByTitle(
                incidentId,
                title,
                orgId,
                List.of(IncidentStatus.SPAM, IncidentStatus.ARCHIVED)
    }
}
