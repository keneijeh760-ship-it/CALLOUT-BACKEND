CREATE TABLE comments (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    incident_id     UUID            NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    author_id       UUID            NOT NULL REFERENCES users(id) ON DELETE CASCADE,

    content         TEXT            NOT NULL,
    is_pinned       BOOLEAN         NOT NULL DEFAULT FALSE,
    pinned_by       UUID            REFERENCES users(id) ON DELETE SET NULL,
    pinned_at       TIMESTAMPTZ,

    is_deleted      BOOLEAN         NOT NULL DEFAULT FALSE,   -- Soft delete
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_comments_incident ON comments(incident_id, created_at ASC);
CREATE INDEX idx_comments_pinned   ON comments(incident_id, is_pinned) WHERE is_pinned = TRUE;