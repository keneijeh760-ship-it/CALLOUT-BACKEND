CREATE TYPE flag_reason AS ENUM (
    'SPAM',             -- Submitted a fake or irrelevant incident
    'FALSE_URGENCY',    -- Marked normal issue as HIGH urgency
    'INAPPROPRIATE',    -- Offensive or abusive content
    'DUPLICATE'         -- Knowingly submitted a duplicate
);

CREATE TABLE user_flags (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reporter_id     UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,  -- User being flagged
    incident_id     UUID        NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    flagged_by      UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,  -- Resolver/mod who flagged
    reason          flag_reason NOT NULL,
    note            TEXT,       -- Optional explanation
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- Can't flag the same reporter on the same incident twice
    UNIQUE (reporter_id, incident_id, flagged_by)
);

CREATE INDEX idx_user_flags_reporter ON user_flags(reporter_id);
CREATE INDEX idx_user_flags_incident ON user_flags(incident_id);






