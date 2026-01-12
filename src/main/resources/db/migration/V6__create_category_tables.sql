CREATE TABLE categories (
                          id         bigserial primary key,
                          parent_id bigint REFERENCES categories(id),
                          is_active BOOLEAN DEFAULT true,
                          created_at   TIMESTAMP not null default now(),
                          updated_at   TIMESTAMP
);

CREATE TABLE category_translations (
                                      id         bigserial primary key,
                                      category_id bigint NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
                                      lang VARCHAR(5) NOT NULL,
                                      title VARCHAR(200) NOT NULL,
                                      slug VARCHAR(200) NOT NULL,
                                      description TEXT,
                                      created_at   TIMESTAMP not null default now(),
                                      updated_at   TIMESTAMP,
                                      CONSTRAINT uq_category_lang UNIQUE (category_id, lang),
                                      CONSTRAINT uq_category_slug_lang UNIQUE (slug, lang)
);
