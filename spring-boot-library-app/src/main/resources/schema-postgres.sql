DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS publisher CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS loan CASCADE;

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
    id               SERIAL              NOT NULL PRIMARY KEY,
    title            VARCHAR             NOT NULL,
    state            CHARACTER VARYING   NOT NULL,
    publisher_id     SERIAL              NOT NULL,
    FOREIGN KEY (publisher_id)  REFERENCES publisher(id)
);

CREATE TABLE loan
(
    id                          SERIAL                   NOT NULL PRIMARY KEY,
    client_id                   VARCHAR                  NOT NULL,
    book_id                     SERIAL                   NOT NULL,
    start_datetime              TIMESTAMP WITH TIME ZONE NOT NULL,
    end_datetime                TIMESTAMP WITH TIME ZONE NOT NULL,
    return_datetime             TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (book_id)  REFERENCES book(id)
);