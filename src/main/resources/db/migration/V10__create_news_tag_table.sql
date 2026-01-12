CREATE TABLE news_tags (
                          news_id bigint REFERENCES news(id) ON DELETE CASCADE,
                          tag_id bigint REFERENCES tags(id) ON DELETE CASCADE,
                          PRIMARY KEY (news_id, tag_id)
);
