package com.dmdev.app.integration;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Book;
import com.dmdev.app.entity.Client;
import com.dmdev.app.util.HibernateTestConfig;
import com.dmdev.app.util.InitialEntityCreator;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractIntegrationTestsClass {

    protected static SessionFactory factory;
    protected final InitialEntityCreator entityCreator = new InitialEntityCreator();
    protected final String userName = "userName";
    protected final String password = "password";
    protected final String firstName = "firstName";
    protected final String secondName = "secondName";
    protected final String middleName = "middleName";

    @BeforeAll
    static void initSessionFactory() {
        factory = HibernateTestConfig.buildSessionFactory();
    }

    @BeforeEach
    void fulfillDatabase() {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            var authorId = session.save(entityCreator.getInitialAuthor());
            var clientId = session.save(entityCreator.getInitialClient());
            session.save(entityCreator.getInitialUser());
            var author = session.get(Author.class, authorId);
            var client = session.get(Client.class, clientId);
            var bookId = session.save(entityCreator.getInitialBook(author));
            var book = session.get(Book.class, bookId);
            session.save(entityCreator.getInitialOrder(book, client));
            session.getTransaction().commit();
        }
    }

    @AfterEach
    void clearDatabase() {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate table orders, book, author, client, users")
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @AfterAll
    public static void closeSessionFactory() {
        factory.close();
    }

}
