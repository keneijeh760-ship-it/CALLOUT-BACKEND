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
@Transactional
public class DuplicateDetectionService {

    private final DuplicateSuggestionRepository duplicateSuggestionRepository;
    private final IncidentRepository incidentRepository;

    public List<DuplicateSuggestion> findPotentialDuplicates(UUID incidentId, String title, UUID orgId){
        List<SimilarIncidentResult> similarIncidents = incidentRepository.findSimilarByTitle(
                incidentId,
                title,
                orgId,
                List.of(IncidentStatus.SPAM, IncidentStatus.ARCHIVED));

        List<DuplicateSuggestion> suggestions = similarIncidents.stream()
                .map(match -> {
                    DuplicateSuggestion suggestion = new DuplicateSuggestion();
                    suggestion.setSourceIncidentId(incidentId);
                    suggestion.setSuggestedIncidentId(match.getId());
                    suggestion.setSimiliarityScore((float) match.getSimilarityScore());
                    suggestion.setAlgorithm(Algorithm.FUZZY);
                    return suggestion;
                })
                .toList();
        return  duplicateSuggestionRepository.saveAll(suggestions);


    }
}
