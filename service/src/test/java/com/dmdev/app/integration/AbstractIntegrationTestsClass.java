package com.dmdev.app.integration;

import com.dmdev.app.repositary.AuthorRepository;
import com.dmdev.app.repositary.BookRepository;
import com.dmdev.app.repositary.ClientRepository;
import com.dmdev.app.repositary.OrderRepository;
import com.dmdev.app.repositary.UserRepository;
import com.dmdev.app.util.InitialEntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public abstract class AbstractIntegrationTestsClass {

    @Autowired
    protected EntityManager em;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected UserRepository userRepository;
    protected final InitialEntityCreator entityCreator = new InitialEntityCreator();

    @Transactional
    @BeforeEach
    void fulfillDatabase() {
        em.createNativeQuery("truncate table orders, book, author, client, users, passport_data")
                .executeUpdate();
        userRepository.save(entityCreator.getInitialUser());
        var author = authorRepository.save(entityCreator.getInitialAuthor());
        var client = clientRepository.save(entityCreator.getInitialClient());
        var book = bookRepository.save(entityCreator.getInitialBook(author));
        orderRepository.save(entityCreator.getInitialOrder(book, client));
    }

//    @AfterEach
//    void clearDatabase() {
//
//        entityManager.createNativeQuery("truncate table orders, book, author, client, users, passport_data")
//                .executeUpdate();
//    }

}
