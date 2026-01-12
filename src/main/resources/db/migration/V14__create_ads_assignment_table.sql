CREATE TABLE ads_assignments
(
    id              bigserial primary key,
    placement_id    bigint      NOT NULL REFERENCES ads_placements (id),
    campaign_id     bigint      NOT NULL REFERENCES ads_campaigns (id),
    creative_id     bigint      NOT NULL REFERENCES ads_creative (id),

    weight          INT                DEFAULT 1,
    lang_filter     JSONB,
    category_filter JSONB,

    start_at        TIMESTAMP NOT NULL,
    end_at          TIMESTAMP,
    is_active       BOOLEAN            DEFAULT true,
    created_at      TIMESTAMP not null default now(),
    updated_at      TIMESTAMP
);

CREATE INDEX idx_ads_assignment_active
    ON ads_assignments (is_active, start_at, end_at);
