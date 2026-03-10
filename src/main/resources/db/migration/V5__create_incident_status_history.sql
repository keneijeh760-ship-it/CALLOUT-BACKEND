CREATE TABLE incident_status_history (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    incident_id     UUID            NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    changed_by      UUID            NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    from_status     incident_status,                -- NULL on first entry (initial submission)
    to_status       incident_status NOT NULL,
    note            TEXT,                           -- Optional resolver note e.g. "Sent to facilities team"
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
    -- No updated_at — this table is append-only. Immutable by design.
);

CREATE INDEX idx_status_history_incident ON incident_status_history(incident_id, created_at ASC);






