package com.phope.realcalloutbackend.aws.sqs.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentSubmittedEvent {
    private UUID incidentId;
    private String title;
    private UUID orgId;
}
