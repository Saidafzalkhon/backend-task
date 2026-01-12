CREATE TABLE ads_creative
(
    id             bigserial primary key,
    campaign_id    bigint        NOT NULL REFERENCES ads_campaigns (id) ON DELETE CASCADE,
    type           VARCHAR(20) NOT NULL,
    landing_url    TEXT,
    image_media_id bigint REFERENCES medias (id),
    html_snippet   TEXT,
    is_active      BOOLEAN              DEFAULT true,
    created_at     TIMESTAMP   not null default now(),
    updated_at     TIMESTAMP
);

CREATE TABLE ads_creative_translations
(
    id          bigserial primary key,
    creative_id bigint       NOT NULL REFERENCES ads_creative (id) ON DELETE CASCADE,
    lang        VARCHAR(5) NOT NULL,
    title       VARCHAR(200),
    alt_text    VARCHAR(200),
    created_at   TIMESTAMP not null default now(),
    updated_at   TIMESTAMP,

    CONSTRAINT uq_creative_lang UNIQUE (creative_id, lang)
);
