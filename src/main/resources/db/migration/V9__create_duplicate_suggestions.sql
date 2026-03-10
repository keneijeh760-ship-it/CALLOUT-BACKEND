CREATE TYPE dedup_algorithm AS ENUM (
    'EXACT',        -- Identical title
    'FUZZY',        -- pg_trgm similarity match
    'SEMANTIC'      -- pgvector cosine similarity (v2)
);

CREATE TABLE duplicate_suggestions (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    source_incident_id      UUID            NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    suggested_incident_id   UUID            NOT NULL REFERENCES incidents(id) ON DELETE CASCADE,
    similarity_score        FLOAT           NOT NULL,   -- 0.0 to 1.0
    algorithm               dedup_algorithm NOT NULL,
    is_resolved             BOOLEAN         NOT NULL DEFAULT FALSE,  -- Reporter acknowledged it
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    -- Don't suggest the same pair twice
    UNIQUE (source_incident_id, suggested_incident_id)
);

CREATE INDEX idx_dedup_source    ON duplicate_suggestions(source_incident_id);
CREATE INDEX idx_dedup_unresolved ON duplicate_suggestions(source_incident_id)
    WHERE is_resolved = FALSE;