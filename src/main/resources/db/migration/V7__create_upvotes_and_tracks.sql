CREATE TABLE upvotes (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    incident_id UUID        NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    user_id     UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE (incident_id, user_id)   -- One upvote per user per incident
);

CREATE INDEX idx_upvotes_incident ON upvotes(incident_id);
CREATE INDEX idx_upvotes_user     ON upvotes(user_id);

CREATE TABLE tracks (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    incident_id UUID        NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    user_id     UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    UNIQUE (incident_id, user_id)   -- One track per user per incident
);

CREATE INDEX idx_tracks_incident ON tracks(incident_id);
CREATE INDEX idx_tracks_user     ON tracks(user_id);