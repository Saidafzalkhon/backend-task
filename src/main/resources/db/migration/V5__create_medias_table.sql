CREATE TABLE medias
(
    id          bigserial primary key,
    storage_key TEXT NOT NULL,
    url         TEXT NOT NULL,
    mime        VARCHAR(100),
    size        BIGINT,
    width       INT,
    height      INT,
    owner_id    bigint REFERENCES users (id),
    is_public   BOOLEAN   DEFAULT false,
    created_at   TIMESTAMP not null default now(),
    updated_at   TIMESTAMP
);

CREATE INDEX idx_media_owner ON medias (owner_id);
