package com.dmdev.app.integration;

import com.dmdev.app.config.ApplicationTestConfig;
import com.dmdev.app.repositary.AuthorRepository;
import com.dmdev.app.repositary.BookRepository;
import com.dmdev.app.repositary.ClientRepository;
import com.dmdev.app.repositary.OrderRepository;
import com.dmdev.app.repositary.UserRepository;
import com.dmdev.app.util.InitialEntityCreator;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

public class AbstractIntegrationTestsClass {

    protected static SessionFactory factory;
    protected final InitialEntityCreator entityCreator = new InitialEntityCreator();

    @BeforeEach
    void fulfillDatabase() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var clientRepository = context.getBean(ClientRepository.class);
        var userRepository = context.getBean(UserRepository.class);
        var bookRepository = context.getBean(BookRepository.class);
        var orderRepository = context.getBean(OrderRepository.class);
        var transaction = session.getTransaction();

        transaction.begin();
        userRepository.save(entityCreator.getInitialUser());
        var author = authorRepository.save(entityCreator.getInitialAuthor());
        var client = clientRepository.save(entityCreator.getInitialClient());
        var book = bookRepository.save(entityCreator.getInitialBook(author));
        orderRepository.save(entityCreator.getInitialOrder(book, client));
        transaction.commit();
    }

    @AfterEach
    void clearDatabase() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var transaction = session.getTransaction();

        transaction.begin();
        session.createNativeQuery("truncate table orders, book, author, client, users, passport_data")
                .executeUpdate();
        session.getTransaction().commit();
    }

}
