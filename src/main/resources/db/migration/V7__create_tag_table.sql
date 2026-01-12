CREATE TABLE tags
(
    id         bigserial primary key,
    code       VARCHAR(100) NOT NULL UNIQUE,
    is_active  BOOLEAN               DEFAULT true,
    created_at TIMESTAMP  not null default now(),
    updated_at TIMESTAMP
);
