CREATE TABLE news (
                      id         bigserial primary key,
                      author_id bigint NOT NULL REFERENCES users(id),
                      category_id bigint REFERENCES categories(id),
                      cover_media_id bigint REFERENCES medias(id),

                      status VARCHAR(20) NOT NULL,
                      is_featured BOOLEAN DEFAULT false,
                      is_deleted BOOLEAN DEFAULT false,

                      publish_at TIMESTAMP,
                      unpublish_at TIMESTAMP,

                      created_at   TIMESTAMP not null default now(),
                      updated_at   TIMESTAMP,
                      deleted_at TIMESTAMP
);

CREATE INDEX idx_news_status_publish
    ON news(status, publish_at DESC);
