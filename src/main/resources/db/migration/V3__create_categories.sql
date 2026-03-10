CREATE TABLE categories (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_id      UUID            NOT NULL REFERENCES organisations(id) ON DELETE CASCADE,
    name        VARCHAR(100)    NOT NULL,
    slug        VARCHAR(100)    NOT NULL,
    description TEXT,
    is_active   BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

    UNIQUE (org_id, slug)
);

CREATE INDEX idx_categories_org_id ON categories(org_id);

-- Now add the FK from users.category_id → categories.id
-- (Couldn't do this in V2 because categories table didn't exist yet)
ALTER TABLE users
    ADD CONSTRAINT fk_users_category
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL;