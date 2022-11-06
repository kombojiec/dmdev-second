package com.dmdev.app.integration;

import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.OrderStatus;
import com.dmdev.app.repositary.AuthorRepository;
import com.dmdev.app.repositary.BookRepository;
import com.dmdev.app.repositary.ClientRepository;
import com.dmdev.app.repositary.OrderRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderIT extends AbstractIntegrationTestsClass {

    @Test
    void createOrder() {
        try (var session = factory.openSession()) {
            var orderRepository = new OrderRepository(session);
            var bookRepository = new BookRepository(session);
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var book = bookRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var client = clientRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var order = orderRepository.save(entityCreator.getTestOrder(book, client));
            session.flush();
            session.clear();

            var createdOrder = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);

            assertThat(createdOrder).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readOrder() {
        try (var session = factory.openSession()) {
            var orderRepository = new OrderRepository(session);
            var bookRepository = new BookRepository(session);
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var book = bookRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var client = clientRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var order = orderRepository.save(entityCreator.getTestOrder(book, client));
            session.flush();
            session.clear();

            var createdOrder = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(createdOrder.getClient()).isEqualTo(client),
                    () -> assertThat(createdOrder.getBook().getId()).isEqualTo(book.getId()),
                    () -> assertThat(createdOrder.getStatus()).isEqualTo(order.getStatus()),
                    () -> assertThat(createdOrder.getIssueDate()).isEqualTo(order.getIssueDate()),
                    () -> assertThat(createdOrder.getReturnDate()).isEqualTo(order.getReturnDate())
            );
            transaction.rollback();
        }

    }

    @Test
    void updateOrder() {
        try (var session = factory.openSession()) {
            LocalDate now = LocalDate.now();
            var authorRepository = new AuthorRepository(session);
            var orderRepository = new OrderRepository(session);
            var bookRepository = new BookRepository(session);
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var book = bookRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var client = clientRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            var order = orderRepository.save(entityCreator.getTestOrder(book, client));
            var author = authorRepository.save(
                    entityCreator.getAuthor("Michail", "Lermontov", "Urievich"));
            var newBook = bookRepository.save(entityCreator.getTestBook(author));
            var newClient = clientRepository.save(
                    entityCreator.getClient("fierst", "second", "middle", null, ClientStatus.ACTIVE));
            session.flush();
            session.clear();

            order = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);
            order.setBook(newBook);
            order.setClient(newClient);
            order.setIssueDate(now);
            order.setReturnDate(now.plusDays(14));
            order.setStatus(OrderStatus.RETURNED);
            var newOrder = orderRepository.save(order);
            session.flush();
            session.clear();
            var updatedOrder = orderRepository.getById(newOrder.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(updatedOrder.getBook()).isEqualTo(newBook),
                    () -> assertThat(updatedOrder.getClient()).isEqualTo(newClient),
                    () -> assertThat(updatedOrder.getIssueDate()).isEqualTo(now),
                    () -> assertThat(updatedOrder.getReturnDate()).isEqualTo(now.plusDays(14)),
                    () -> assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.RETURNED)
            );
            transaction.commit();
        }
    }

    @Test
    void deleteOrder() {
        try (var session = factory.openSession()) {
            var orderRepository = new OrderRepository(session);
            var transaction = session.beginTransaction();
            var order = orderRepository.get().stream().findFirst().orElseThrow(IllegalArgumentException::new);
            session.flush();
            session.clear();

            session.delete(order);
            session.flush();
            var deletedOrder = orderRepository.getById(order.getId());

            assertThat(deletedOrder.isEmpty()).isTrue();
            transaction.commit();
        }
    }
}
