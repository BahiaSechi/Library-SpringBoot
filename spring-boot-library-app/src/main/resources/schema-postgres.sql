DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS publisher CASCADE;
DROP TABLE IF EXISTS book CASCADE;

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

CREATE TABLE book
(
    id     SERIAL              NOT NULL PRIMARY KEY,
    title  VARCHAR             NOT NULL,
    state  CHARACTER VARYING   NOT NULL
);