package com.dmdev.app;

import com.dmdev.app.entity.*;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.Genre;
import com.dmdev.app.enums.OrderStatus;
import com.dmdev.app.enums.Role;
import com.dmdev.app.util.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        try (SessionFactory factory = HibernateConfig.buildSessionFactory()) {
            var address = Address.builder()
                    .city("New York")
                    .country("USA")
                    .street("Wall Street")
                    .houseNumber("126B")
                    .build();
            var author = Author.builder()
                    .initials(Initials.builder()
                            .firstName("Alexander")
                            .secondName("Pushkin")
                            .middleName("Sergeevich")
                            .build())
                    .build();
            var book = Book.builder()
                    .author(author)
                    .name("Dubrovskiy")
                    .genre(Genre.POETRY)
                    .pageSize(350)
                    .publishDate(LocalDate.of(1835, 5, 25))
                    .build();
            var client = Client.builder()
                    .initials(Initials.builder()
                            .firstName("John")
                            .secondName("Rambo")
                            .middleName("Arnoldovich")
                            .build())
                    .address(address)
                    .status(ClientStatus.ACTIVE)
                    .build();
            var order = Order.builder()
                    .book(book)
                    .issueDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusDays(14))
                    .status(OrderStatus.ISSUED)
                    .client(client)
                    .book(book)
                    .build();
            var user = User.builder()
                    .role(Role.ADMIN)
                    .initials(Initials.builder()
                            .firstName("Tamara")
                            .secondName("<Bibliotekar")
                            .middleName("Konstantinovna")
                            .build())
                    .username("Toma")
                    .password("qwerty")
                    .build();
            try (Session session = factory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(author);
                session.save(book);
                session.save(client);
                session.save(order);
                session.save(user);
                transaction.commit();
            }
        }
    }

}
