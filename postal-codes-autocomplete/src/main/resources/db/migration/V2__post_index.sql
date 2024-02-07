CREATE TABLE POST_INDEX
(
    IDX TEXT NOT NULL PRIMARY KEY,
    NAME TEXT NOT NULL,
    REGION TEXT NOT NULL,
    AREA TEXT NOT NULL,
    AUTONOM TEXT NOT NULL,
    CITY TEXT NOT NULL,
    CITY1 TEXT NOT NULL,
    CONSTRAINT pi_idx_length CHECK (length(IDX) = 6)
);

SET search_path to public,post;
-- create extension pg_trgm with schema pg_catalog;;
CREATE INDEX PI_INDEX_IDX ON POST_INDEX USING gin(IDX gin_trgm_ops);
CREATE INDEX PI_NAME_IDX ON POST_INDEX USING gin(NAME gin_trgm_ops);
CREATE INDEX PI_REGION_IDX ON POST_INDEX USING gin(REGION gin_trgm_ops);
CREATE INDEX PI_AREA_IDX ON POST_INDEX USING gin(AREA gin_trgm_ops);
CREATE INDEX PI_AUTONOM_IDX ON POST_INDEX USING gin(AUTONOM gin_trgm_ops);
CREATE INDEX PI_CITY_IDX ON POST_INDEX USING gin(CITY gin_trgm_ops);
CREATE INDEX PI_CITY1_IDX ON POST_INDEX USING gin(CITY1 gin_trgm_ops);