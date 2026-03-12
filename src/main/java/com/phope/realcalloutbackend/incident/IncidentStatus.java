package com.phope.realcalloutbackend.incident;

public enum IncidentStatus {
    PENDING,
    ACKNOWLEDGED,
    IN_PROGRESS,
    RESOLVED,
    ARCHIVED,
    SPAM;

    public boolean canTransitionTo(IncidentStatus next) {
        return switch (this) {
            case PENDING -> next == ACKNOWLEDGED || next == SPAM;
            case ACKNOWLEDGED -> next == IN_PROGRESS || next == SPAM;
            case IN_PROGRESS -> next == RESOLVED || next == ACKNOWLEDGED;
            case RESOLVED -> next == ARCHIVED;
            case ARCHIVED,
                 SPAM -> false;
        };
    }

}
