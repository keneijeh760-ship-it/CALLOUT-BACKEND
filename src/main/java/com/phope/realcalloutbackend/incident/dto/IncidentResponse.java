package com.phope.realcalloutbackend.incident.dto;

import com.phope.realcalloutbackend.incident.IncidentStatus;
import com.phope.realcalloutbackend.incident.IncidentUrgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentResponse {
    private UUID id;
    private String title;
    private String description;
    private UUID categoryId;
    private String locationTag;
    private IncidentStatus status;
    private IncidentUrgency urgency;
    private int upvoteCount;
    private int trackCount;
    private List<String> attachmentUrls = new ArrayList<>();
    private Instant createdAt;
}
