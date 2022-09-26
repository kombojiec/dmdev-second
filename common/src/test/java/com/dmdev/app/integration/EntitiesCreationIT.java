package com.dmdev.app.integration;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Book;
import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Order;
import com.dmdev.app.entity.User;
import com.dmdev.app.util.EntitiesUtil;
import com.dmdev.app.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntitiesCreationIT extends IntegrationParent{

    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Test
    void addUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(EntitiesUtil.USER);
            session.getTransaction().commit();
        }
        assertThat(EntitiesUtil.USER.getId()).isEqualTo(3L);
    }

    @Test
    void checkFieldsForMappedUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(EntitiesUtil.USER);
            session.getTransaction().commit();
            session.getTransaction();
            var user = session.get(User.class, id);
            assertThat(EntitiesUtil.USER).isEqualTo(user);
        }
    }

    @Test
    void addAuthor() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(EntitiesUtil.AUTHOR);
            session.getTransaction().commit();
        }
        assertThat(EntitiesUtil.AUTHOR.getId()).isEqualTo(2L);
    }

    @Test
    void checkFieldsForMappedAuthor() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(EntitiesUtil.AUTHOR);
            session.getTransaction().commit();
            session.getTransaction();
            var author = session.get(Author.class, id);
            assertThat(EntitiesUtil.AUTHOR).isEqualTo(author);
        }
    }

    @Test
    void addClient() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(EntitiesUtil.CLIENT);
            session.getTransaction().commit();
        }
        assertThat(EntitiesUtil.CLIENT.getId()).isEqualTo(2L);
    }

    @Test
    void checkFieldsForMappedClient() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(EntitiesUtil.CLIENT);
            session.getTransaction().commit();
            session.getTransaction();
            var client = session.get(Client.class, id);
            assertThat(EntitiesUtil.CLIENT).isEqualTo(client);
        }
    }

    @Test
    void addBook() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(EntitiesUtil.BOOK);
            session.getTransaction().commit();
        }
        assertThat(EntitiesUtil.BOOK.getId()).isEqualTo(2L);
    }

    @Test
    void checkFieldsForMappedBook() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(EntitiesUtil.BOOK);
            session.getTransaction().commit();
            session.getTransaction();
            var book = session.get(Book.class, id);
            assertThat(EntitiesUtil.BOOK).isEqualTo(book);
        }
    }

    @Test
    void addOrder() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(EntitiesUtil.ORDER);
            session.getTransaction().commit();
        }
        assertThat(EntitiesUtil.ORDER.getId()).isEqualTo(2L);
    }

    @Test
    void checkFieldsForMappedOrder() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(EntitiesUtil.ORDER);
            session.getTransaction().commit();
            session.getTransaction();
            var order = session.get(Order.class, id);
            assertThat(EntitiesUtil.ORDER).isEqualTo(order);
        }
    }

}
