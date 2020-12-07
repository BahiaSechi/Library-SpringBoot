DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS publisher CASCADE;

CREATE TABLE author
(
    id     SERIAL            NOT NULL PRIMARY KEY,
    name   VARCHAR           NOT NULL
);

CREATE TABLE publisher
(
    id     SERIAL            NOT NULL PRIMARY KEY,
    name   VARCHAR           NOT NULL
);