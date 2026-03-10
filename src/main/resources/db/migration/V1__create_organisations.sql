CREATE TABLE organisations (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255)        NOT NULL,
    slug        VARCHAR(100)        NOT NULL UNIQUE,   -- e.g. "pau" — used in URLs
    domain      VARCHAR(255)        NOT NULL UNIQUE,   -- e.g. "pau.edu.ng" — enforces email restriction
    logo_url    TEXT,
    is_active   BOOLEAN             NOT NULL DEFAULT TRUE,
    settings    JSONB               NOT NULL DEFAULT '{}',  -- extensible config per org
    created_at  TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ         NOT NULL DEFAULT NOW()
);

-- Index for domain lookup — happens on every signup to validate email
CREATE INDEX idx_organisations_domain ON organisations(domain);
CREATE INDEX idx_organisations_slug   ON organisations(slug);

-- Seed PAU as the first organisation
INSERT INTO organisations (name, slug, domain)
VALUES ('Pan-Atlantic University', 'pau', 'pau.edu.ng');