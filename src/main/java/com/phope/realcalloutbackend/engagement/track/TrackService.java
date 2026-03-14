package com.phope.realcalloutbackend.engagement.track;
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
public class TrackService {

    private final TrackRepository trackRepository;
    private final IncidentRepository incidentRepository;
    private final UserService userService;

    @Transactional
    public void addTrack(UUID incidentId, Jwt jwt) {

        User user = userService.getOrCreateUser(jwt);

        boolean alreadyTracking = trackRepository
                .existsByIncidentIdAndUserId(incidentId, user.getId());

        if (alreadyTracking) {
            throw new CalloutException("You are already tracking this incident");
        }

        Track track = new Track();
        track.setIncidentId(incidentId);
        track.setUserId(user.getId());
        trackRepository.save(track);

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NotFoundException("Incident", incidentId));

        incident.setTrackCount(incident.getTrackCount() + 1);
        incidentRepository.save(incident);
    }

    @Transactional
    public void removeTrack(UUID incidentId, Jwt jwt) {

        User user = userService.getOrCreateUser(jwt);

        Track track = trackRepository
                .findByIncidentIdAndUserId(incidentId, user.getId())
                .orElseThrow(() -> new NotFoundException("Track", incidentId));

        trackRepository.delete(track);

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NotFoundException("Incident", incidentId));

        incident.setTrackCount(Math.max(0, incident.getTrackCount() - 1));
        incidentRepository.save(incident);
    }
}