DROP TABLE IF EXISTS author CASCADE;

CREATE TABLE author
(
    id     SERIAL            NOT NULL PRIMARY KEY,
    name   VARCHAR           NOT NULL
);