package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
public class OrderIT extends AbstractIntegrationTestsClass {

    @Test
    void createOrder() {
        var book = bookRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var client = clientRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var order = orderRepository.save(entityCreator.getTestOrder(book, client));
        em.flush();
        em.clear();

        var createdOrder = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdOrder).isNotNull();
    }

    @Test
    void readOrder() {
        var book = bookRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var client = clientRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var order = orderRepository.save(entityCreator.getTestOrder(book, client));
        em.flush();
        em.clear();

        var createdOrder = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdOrder.getClient()).isEqualTo(client),
                () -> assertThat(createdOrder.getBook().getId()).isEqualTo(book.getId()),
                () -> assertThat(createdOrder.getStatus()).isEqualTo(order.getStatus()),
                () -> assertThat(createdOrder.getIssueDate()).isEqualTo(order.getIssueDate()),
                () -> assertThat(createdOrder.getReturnDate()).isEqualTo(order.getReturnDate())
        );
    }

    @Test
    void updateOrder() {
        LocalDate now = LocalDate.now();
        var book = bookRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var client = clientRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        var order = orderRepository.save(entityCreator.getTestOrder(book, client));
        var author = authorRepository.save(
                entityCreator.getAuthor("Michail", "Lermontov", "Urievich"));
        var newBook = bookRepository.save(entityCreator.getTestBook(author));
        var newClient = clientRepository.save(
                entityCreator.getClient("fierst", "second", "middle", null, ClientStatus.ACTIVE));
        em.flush();
        em.clear();

        order = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);
        order.setBook(newBook);
        order.setClient(newClient);
        order.setIssueDate(now);
        order.setReturnDate(now.plusDays(14));
        order.setStatus(OrderStatus.RETURNED);
        var newOrder = orderRepository.save(order);
        em.flush();
        em.clear();
        var updatedOrder = orderRepository.getById(newOrder.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedOrder.getBook()).isEqualTo(newBook),
                () -> assertThat(updatedOrder.getClient()).isEqualTo(newClient),
                () -> assertThat(updatedOrder.getIssueDate()).isEqualTo(now),
                () -> assertThat(updatedOrder.getReturnDate()).isEqualTo(now.plusDays(14)),
                () -> assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.RETURNED)
        );
    }

    @Test
    void deleteOrder() {
        var order = orderRepository.getAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        em.flush();
        em.clear();

        order = orderRepository.getById(order.getId()).orElseThrow(IllegalArgumentException::new);
        orderRepository.delete(order);
        var deletedOrder = orderRepository.getById(order.getId());

        assertThat(deletedOrder.isEmpty()).isTrue();
    }
}
