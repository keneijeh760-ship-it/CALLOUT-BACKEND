package com.phope.realcalloutbackend.engagement.upvote;

import com.phope.realcalloutbackend.Shared.config.exception.CalloutException;
import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.incident.Incident;
import com.phope.realcalloutbackend.incident.IncidentRepository;
import com.phope.realcalloutbackend.user.User;
import com.phope.realcalloutbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UpvoteService {

    private final UpvoteRepository upvoteRepository;
    private final IncidentRepository incidentRepository;
    private final UserService userService;

    @Transactional
    public void addUpvote(UUID incidentId, Jwt jwt) {

        // Step 1 — who is doing this?
        User user = userService.getOrCreateUser(jwt);

        // Step 2 — have they already upvoted?
        // Check BEFORE creating — prevent duplicates
        boolean alreadyUpvoted = upvoteRepository
                .existsByIncidentIdAndUserId(incidentId, user.getId());

        if (alreadyUpvoted) {
            throw new CalloutException("You have already upvoted this incident");
        }

        // Step 3 — create and save the upvote
        Upvote upvote = new Upvote();
        upvote.setIncidentId(incidentId);
        upvote.setUserId(user.getId());
        upvoteRepository.save(upvote);

        // Step 4 — increment the denormalised counter on the incident
        // Must stay in sync — this is the same transaction so both
        // the upvote save and counter update succeed or fail together
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NotFoundException("Incident", incidentId));

        incident.setUpvoteCount(incident.getUpvoteCount() + 1);
        incidentRepository.save(incident);
    }

    @Transactional
    public void removeUpvote(UUID incidentId, Jwt jwt) {

        User user = userService.getOrCreateUser(jwt);


        Upvote upvote = upvoteRepository
                .findByIncidentIdAndUserId(incidentId, user.getId())
                .orElseThrow(() -> new NotFoundException("Upvote", incidentId));


        upvoteRepository.delete(upvote);


        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NotFoundException("Incident", incidentId));

        incident.setUpvoteCount(Math.max(0, incident.getUpvoteCount() - 1));
        incidentRepository.save(incident);
    }
}