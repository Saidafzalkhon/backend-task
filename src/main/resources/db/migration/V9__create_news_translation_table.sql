CREATE TABLE news_translations
(
    id               bigserial primary key,
    news_id          bigint       NOT NULL REFERENCES news (id) ON DELETE CASCADE,
    lang             VARCHAR(5)   NOT NULL,

    title            VARCHAR(200) NOT NULL,
    slug             VARCHAR(200) NOT NULL,
    summary          TEXT,
    content          TEXT         NOT NULL,

    meta_title       VARCHAR(200),
    meta_description TEXT,
    created_at       TIMESTAMP    not null default now(),
    updated_at       TIMESTAMP,
    search_fts       tsvector,

    CONSTRAINT uq_news_lang UNIQUE (news_id, lang),
    CONSTRAINT uq_news_slug_lang UNIQUE (slug, lang)
);

CREATE INDEX idx_news_translation_slug_lang
    ON news_translations (slug, lang);
CREATE INDEX idx_news_translation_fts ON news_translations USING GIN (search_fts)
