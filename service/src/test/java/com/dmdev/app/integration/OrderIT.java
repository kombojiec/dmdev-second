package com.dmdev.app.integration;

import com.dmdev.app.entity.Book;
import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Order;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderIT extends AbstractIntegrationTestsClass {

    @Test
    void createOrder() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var book = session.get(Book.class, 1);
            var client = session.get(Client.class, 1);
            var order = entityCreator.getTestOrder(book, client);
            var orderId = session.save(order);
            session.flush();
            session.clear();

            order = session.get(Order.class, orderId);

            assertThat(order).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readOrder() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var book = session.get(Book.class, 1);
            var client = session.get(Client.class, 1);
            var order = entityCreator.getTestOrder(book, client);
            var orderId = session.save(order);
            session.flush();
            session.clear();

            var createdOrder = session.get(Order.class, orderId);

            assertAll(
                    () -> assertThat(createdOrder.getClient()).isEqualTo(client),
                    () -> assertThat(createdOrder.getBook()).isEqualTo(book),
                    () -> assertThat(createdOrder.getStatus()).isEqualTo(order.getStatus()),
                    () -> assertThat(createdOrder.getIssueDate()).isEqualTo(order.getIssueDate()),
                    () -> assertThat(createdOrder.getReturnDate()).isEqualTo(order.getReturnDate())
            );
            transaction.commit();
        }

    }

    @Test
    void updateOrder() {
        try (var session = factory.openSession()) {
            LocalDate now = LocalDate.now();
            var transaction = session.beginTransaction();
            var book = session.get(Book.class, 1);
            var client = session.get(Client.class, 1);
            var order = entityCreator.getTestOrder(book, client);
            var orderId = session.save(order);
            var author = entityCreator.getAuthor("Michail", "Lermontov", "Urievich");
            var newBook = entityCreator.getTestBook(author);
            var newClient = entityCreator.getClient("fierst", "second", "middle",
                    null, ClientStatus.ACTIVE);
            session.save(author);
            var newBookId = session.save(newBook);
            var newClientId = session.save(newClient);
            session.flush();
            session.clear();

            order = session.get(Order.class, orderId);
            order.setBook(session.get(Book.class, newBookId));
            order.setClient(session.get(Client.class, newClientId));
            order.setIssueDate(now);
            order.setReturnDate(now.plusDays(14));
            order.setStatus(OrderStatus.RETURNED);
            var newOrderId = session.save(order);
            session.flush();
            session.clear();
            var updatedOrder = session.get(Order.class, newOrderId);

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
            var transaction = session.beginTransaction();
            var book = session.get(Book.class, 1);
            var client = session.get(Client.class, 1);
            var order = entityCreator.getTestOrder(book, client);
            var orderId = session.save(order);
            session.flush();
            session.clear();

            session.delete(order);
            session.flush();
            order = session.get(Order.class, orderId);

            assertThat(order).isNull();
            transaction.commit();
        }
    }
}
