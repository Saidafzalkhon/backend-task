CREATE
OR REPLACE FUNCTION fts_match(tsvector, text)
RETURNS boolean AS $$
SELECT $1 @@ plainto_tsquery($2);
$$
LANGUAGE sql IMMUTABLE;
