package com.dmdev.app.integration;

import com.dmdev.app.util.ConnectionManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationParent {

    private static final String DROP_TABLES = """
            DROP TABLE IF EXISTS lbr_order;
            DROP TABLE IF EXISTS lbr_book;
            DROP TABLE IF EXISTS lbr_author;
            DROP TABLE IF EXISTS lbr_client;
            DROP TABLE IF EXISTS lbr_user;
            """;

    private static final String CREATE_TABLES = """
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
            """;

    private static final String INSERT_DATA = """
            INSERT INTO lbr_user (first_name, second_name, middle_name, role)
            VALUES ('Иван', 'Иванов', 'Иванович', 'ADMIN'),
                   ('Василий', 'Васин', 'Васильевич', 'LIBRARIAN');
                        
            INSERT INTO lbr_client (first_name, second_name, middle_name, country, city, street, house_number, client_status)
            VALUES ('Пётр', 'Петров', 'Петрович', 'Россия', 'Москва', 'Тверская', '12', 'BLOCKED');
                        
            INSERT INTO lbr_author(FIRST_NAME, SECOND_NAME, MIDDLE_NAME)
            VALUES ('Александр', 'Пушкин', 'Сергеевич');
                        
            INSERT INTO lbr_book(genre, name, author)
            VALUES('NOVEL', 'Дубровский', 1);
                        
            INSERT INTO lbr_order(client_id, book_id, issue_date, return_date, order_status)
            VALUES (1, 1, '2022-08-25T14:00:00.000Z', '2022-09-09T14:00:00.000Z', 'EXPIRED');
            """;

    @SneakyThrows
    @BeforeEach
    void prepareDatabase() {
        try(var connection = ConnectionManager.get();
        var statement = connection.createStatement()) {
            statement.execute(DROP_TABLES);
            statement.execute(CREATE_TABLES);
            statement.execute(INSERT_DATA);
        }
    }

}
