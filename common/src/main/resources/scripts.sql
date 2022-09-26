CREATE TABLE IF NOT EXISTS lbr_user
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL,
    role        VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS lbr_client
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

CREATE TABLE IF NOT EXISTS lbr_order
(
    id          SERIAL PRIMARY KEY,
    client_id   BIGINT    NOT NULL,
    book_id     BIGINT    NOT NULL,
    issue_date  TIMESTAMP NOT NULL,
    return_date TIMESTAMP NOT NULL,
    order_status VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS lbr_book
(
    id     SERIAL PRIMARY KEY,
    genre  VARCHAR(128),
    name   VARCHAR(256) NOT NULL,
    author BIGINT       NOT NULL
);

CREATE TABLE IF NOT EXISTS lbr_author
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL
);