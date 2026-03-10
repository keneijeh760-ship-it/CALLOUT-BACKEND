CREATE TYPE user_role AS ENUM ('REPORTER', 'RESOLVER', 'MODERATOR');

CREATE TABLE users (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_id              UUID            NOT NULL REFERENCES organisations(id) ON DELETE CASCADE,
    supabase_uid        VARCHAR(255)    NOT NULL UNIQUE,  -- Supabase Auth user ID (sub claim in JWT)
    username            VARCHAR(50)     NOT NULL,         -- Anonymous handle chosen at signup
    email_hash          VARCHAR(255)    NOT NULL,         -- Hashed email — we verify domain but don't store plaintext
    role                user_role       NOT NULL DEFAULT 'REPORTER',
    category_id         UUID,                             -- For RESOLVER: which category they handle (FK added in V3)
    trust_score         INTEGER         NOT NULL DEFAULT 100,
    flag_count          INTEGER         NOT NULL DEFAULT 0,
    is_suspended        BOOLEAN         NOT NULL DEFAULT FALSE,
    is_banned           BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    last_active_at      TIMESTAMPTZ,

    -- A username must be unique within an org (two orgs can have same username)
    UNIQUE (org_id, username)
);

CREATE INDEX idx_users_org_id       ON users(org_id);
CREATE INDEX idx_users_supabase_uid ON users(supabase_uid);
CREATE INDEX idx_users_role         ON users(org_id, role);