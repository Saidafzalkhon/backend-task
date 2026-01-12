CREATE TABLE ads_placements (
                               id         bigserial primary key,
                               code VARCHAR(100) NOT NULL UNIQUE,
                               title VARCHAR(200),
                               description TEXT,
                               is_active BOOLEAN DEFAULT true,
                               created_at   TIMESTAMP not null default now(),
                               updated_at   TIMESTAMP
);

CREATE TABLE ads_campaigns (
                              id         bigserial primary key,
                              name VARCHAR(200) NOT NULL,
                              advertiser VARCHAR(200),
                              status VARCHAR(20) NOT NULL,
                              start_at TIMESTAMP NOT NULL,
                              end_at TIMESTAMP,
                              daily_cap_impressions INT,
                              daily_cap_clicks INT,
                              created_at   TIMESTAMP not null default now(),
                              updated_at   TIMESTAMP
);
