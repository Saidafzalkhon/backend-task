CREATE TABLE news_history (
                              id         bigserial primary key,
                              news_id bigint NOT NULL REFERENCES news(id) ON DELETE CASCADE,
                              changed_by bigint NOT NULL REFERENCES users(id),
                              from_status VARCHAR(20),
                              to_status VARCHAR(20),
                              diff_json JSONB,
                              changed_at TIMESTAMP DEFAULT now(),
                              created_at   TIMESTAMP not null default now(),
                              updated_at   TIMESTAMP
);
