package com.dmdev.app.util;

import com.dmdev.app.entity.Address;
import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Book;
import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Order;
import com.dmdev.app.entity.User;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.Genre;
import com.dmdev.app.enums.OrderStatus;
import com.dmdev.app.enums.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntitiesUtil {

    public static final User USER = User.builder()
            .firstName("Тамара")
            .secondName("Библиотекарь")
            .middleName("Константиновна")
            .role(Role.LIBRARIAN)
            .build();

    public static final Client CLIENT = Client.builder()
            .firstName("Юрий")
            .secondName("Гагарин")
            .middleName("Алексеевич")
            .address(Address.builder()
                    .country("СССР")
                    .city( "Смоленск")
                    .street("Главная")
                    .houseNumber("36A")
                    .build())
            .clientStatus(ClientStatus.DELETED)
            .build();

    public static final Author AUTHOR = Author.builder()
            .firstName("Михаил")
            .secondName("Лермонтов")
            .middleName("Юрьевич")
            .build();

    public static final Book BOOK = Book.builder()
            .genre(Genre.POETRY)
            .author(2L)
            .name("Мцыри")
            .build();

    public static final Order ORDER = Order.builder()
            .client(1L)
            .book(1L)
            .issueDate(LocalDateTime.parse("2020-08-25T14:00:00.000", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .returnDate(LocalDateTime.parse("2020-08-30T14:00:00.000", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .orderStatus(OrderStatus.RETURNED)
            .build();
}
