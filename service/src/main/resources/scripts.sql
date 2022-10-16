CREATE TABLE IF NOT EXISTS users
(
    id          SERIAL PRIMARY KEY,
    username    VARCHAR(128) NOT NULL UNIQUE,
    password    VARCHAR(128) NOT NULL,
    first_name  VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL,
    role        VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS client
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(128) NOT NULL,
    second_name   VARCHAR(128) NOT NULL,
    middle_name   VARCHAR(128) NOT NULL,
    country       VARCHAR(128) NOT NULL,
    city          VARCHAR(128) NOT NULL,
    street        VARCHAR(128) NOT NULL,
    house_number  VARCHAR(128) NOT NULL,
    client_status VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id           SERIAL PRIMARY KEY,
    client_id    INTEGER      NOT NULL REFERENCES lbr_client (id),
    book_id      BIGINT       NOT NULL REFERENCES lbr_book (id),
    issue_date   TIMESTAMP    NOT NULL,
    return_date  TIMESTAMP    NOT NULL,
    status VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS book
(
    id     SERIAL PRIMARY KEY,
    genre  VARCHAR(128),
    name   VARCHAR(256) NOT NULL,
    author_id INTEGER      NOT NULL REFERENCES lbr_author (id),
    publish_date TIMESTAMP ,
    page_size INTEGER NOT NULL,
    is_issued bool
);

CREATE TABLE IF NOT EXISTS author
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL
);