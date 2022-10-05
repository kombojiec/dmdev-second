package com.dmdev.app.util;

import com.dmdev.app.entity.*;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.enums.Genre;
import com.dmdev.app.enums.OrderStatus;
import com.dmdev.app.enums.Role;

import java.time.LocalDate;

public class InitialEntityCreator {

    public Address getAddress(String city, String country, String street, String houseNumber) {
        return Address.builder()
                .city(city)
                .country(country)
                .street(street)
                .houseNumber(houseNumber)
                .build();
    }

    public Author getAuthor(String firstName, String secondName, String middleName) {
        return Author.builder()
                .initials(Initials.builder()
                        .firstName(firstName)
                        .secondName(secondName)
                        .middleName(middleName)
                        .build())
                .build();
    }

    public Book getBook(Author author, String name, Genre genre, int pageSize, LocalDate publishDate) {
        return Book.builder()
                .author(author)
                .name(name)
                .genre(genre)
                .pageSize(pageSize)
                .publishDate(publishDate)
                .isIssued(true)
                .build();
    }

    public Client getClient(String firstName, String secondName, String middleName, Address address,
                            ClientStatus status) {
        return Client.builder()
                .initials(Initials.builder()
                        .firstName(firstName)
                        .secondName(secondName)
                        .middleName(middleName)
                        .build())
                .address(address)
                .status(status)
                .build();
    }

    public Order getOrder(Book book, LocalDate issueDate, LocalDate returnDate, OrderStatus status,
                          Client client) {
        return Order.builder()
                .book(book)
                .issueDate(issueDate)
                .returnDate(returnDate)
                .status(status)
                .client(client)
                .build();
    }

    public User getUser(Role role, String firstName, String secondName, String middleName, String username,
                        String password) {
        return User.builder()
                .role(role)
                .initials(Initials.builder()
                        .firstName(firstName)
                        .secondName(secondName)
                        .middleName(middleName)
                        .build())
                .username(username)
                .password(password)
                .build();
    }

    public Author getInitialAuthor() {
        return getAuthor("Alexander", "Pushkin", "Sergeevich");
    }

    public Book getInitialBook(Author author) {
        return getBook(author, "Dubrovskiy", Genre.POETRY, 350,
                LocalDate.of(1835, 5, 25));
    }

    public Client getInitialClient() {
        var address = getAddress("New York", "USA", "Wall Street", "126B");
        return getClient("John", "Rambo", "Arnol'dovich", address, ClientStatus.ACTIVE);
    }

    public Order getInitialOrder(Book book, Client client) {
        return getOrder(book, LocalDate.now(), LocalDate.now().plusDays(14), OrderStatus.ISSUED, client);
    }

    public User getInitialUser() {
        return getUser(Role.ADMIN, "Tamara", "Bibliotekar", "Konstantinovna",
                "Toma", "qwerty");
    }

    public User getTestUser() {
        return getUser(Role.ADMIN, "Васильева", "Надежда", "Фёдоровна",
                "librarian", "qwerty");
    }

    public Author getTestAuthor() {
        return getAuthor("James", "Chase", "Hadley");
    }

    public Book getTestBook(Author author) {
        return getBook(author, "book name", Genre.FANTASY, 789,
                LocalDate.of(1967, 3, 15));
    }

    public Client getTestClient() {
        Address address = getAddress("Chicago", "USA", "First street", "94C");
        return getClient("William", "Gates", "Henry", address, ClientStatus.ACTIVE);
    }

    public Order getTestOrder(Book book, Client client) {
        return getOrder(book, LocalDate.now(), LocalDate.now().plusDays(14), OrderStatus.ISSUED, client);
    }

}
