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

CREATE UNIQUE INDEX POST_INDEX_IDX_UNIQUE_IDX ON POST_INDEX (IDX);
