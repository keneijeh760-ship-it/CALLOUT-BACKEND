CREATE TYPE incident_status AS ENUM (
    'PENDING',       -- Submitted, not yet seen by authority
    'ACKNOWLEDGED',  -- Authority has seen it, not started yet
    'IN_PROGRESS',   -- Confirmed real, being worked on
    'RESOLVED',      -- Fixed
    'ARCHIVED',      -- Resolved 24hrs ago — hidden from feed, still searchable
    'SPAM'           -- Fake/duplicate/inappropriate — hidden from everything
);

CREATE TYPE incident_urgency AS ENUM ('NORMAL', 'HIGH');

CREATE TABLE incidents (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_id          UUID                NOT NULL REFERENCES organisations(id) ON DELETE CASCADE,
    reporter_id     UUID                NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id     UUID                NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
    assigned_to     UUID                REFERENCES users(id) ON DELETE SET NULL,  -- Resolver assigned by moderator

    title           VARCHAR(255)        NOT NULL,
    description     TEXT                NOT NULL,
    location_tag    VARCHAR(255),                   -- Free text e.g. "Block C, Room 204"
    attachment_urls TEXT[]              NOT NULL DEFAULT '{}',  -- S3 object keys (not full URLs)

    status          incident_status     NOT NULL DEFAULT 'PENDING',
    urgency         incident_urgency    NOT NULL DEFAULT 'NORMAL',
    is_urgent_flagged BOOLEAN           NOT NULL DEFAULT FALSE,  -- Flagged by resolver for false urgency

    upvote_count    INTEGER             NOT NULL DEFAULT 0,      -- Denormalized for fast sorting
    track_count     INTEGER             NOT NULL DEFAULT 0,

    created_at      TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    resolved_at     TIMESTAMPTZ,
    archived_at     TIMESTAMPTZ
);

-- Feed query index — the most critical query in the system
-- Covers: org filter + status filter + sort by urgency + created_at
CREATE INDEX idx_incidents_feed ON incidents(org_id, status, urgency, created_at DESC);

-- Search by category
CREATE INDEX idx_incidents_category ON incidents(org_id, category_id);

-- Reporter's own incidents
CREATE INDEX idx_incidents_reporter ON incidents(reporter_id);

-- Archival job — find resolved incidents older than 24hrs
CREATE INDEX idx_incidents_resolved_at ON incidents(resolved_at)
    WHERE status = 'RESOLVED';

-- Enable pg_trgm for fuzzy duplicate detection on title
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_incidents_title_trgm ON incidents USING GIN (title gin_trgm_ops);