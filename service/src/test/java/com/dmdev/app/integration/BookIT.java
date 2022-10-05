package com.dmdev.app.integration;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Book;
import com.dmdev.app.enums.Genre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookIT extends AbstractIntegrationTestsClass {

    @Test
    void createBook() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var author = session.get(Author.class, 1);
            var book = entityCreator.getTestBook(author);
            var bookId = session.save(book);
            session.flush();
            session.clear();
            book = session.get(Book.class, bookId);
            assertThat(book).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readBook() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var author = session.get(Author.class, 1);
            var book = entityCreator.getTestBook(author);
            var bookId = session.save(book);
            session.flush();
            session.clear();
            var createdBook = session.get(Book.class, bookId);
            assertAll(
                    () -> assertThat(createdBook.getId()).isEqualTo(bookId),
                    () -> assertThat(createdBook.getAuthor()).isEqualTo(author),
                    () -> assertThat(createdBook.getGenre()).isEqualTo(book.getGenre()),
                    () -> assertThat(createdBook.getName()).isEqualTo(book.getName()),
                    () -> assertThat(createdBook.getPageSize()).isEqualTo(book.getPageSize()),
                    () -> assertThat(createdBook.getPublishDate()).isEqualTo(book.getPublishDate())
            );
            transaction.commit();
        }
    }

    @Test
    void updateBook() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var author = session.get(Author.class, 1);
            var book = entityCreator.getTestBook(author);
            var bookId = session.save(book);
            var newAuthorId = session.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            book = session.get(Book.class, bookId);
            var newAuthor = session.get(Author.class, newAuthorId);
            book.setName("new name");
            book.setAuthor(newAuthor);
            book.setGenre(Genre.LYRIC);
            book.setPageSize(333);
            book.setIssued(true);
            book.setPublishDate(LocalDate.of(2222, 2, 22));

            session.update(book);
            session.flush();
            session.clear();

            var updatedBook = session.get(Book.class, bookId);
            var finalBook = book;
            assertAll(
                    () -> assertThat(updatedBook.getName()).isEqualTo(finalBook.getName()),
                    () -> assertThat(updatedBook.getAuthor()).isEqualTo(finalBook.getAuthor()),
                    () -> assertThat(updatedBook.getGenre()).isEqualTo(finalBook.getGenre()),
                    () -> assertThat(updatedBook.getPageSize()).isEqualTo(finalBook.getPageSize()),
                    () -> assertThat(updatedBook.isIssued()).isEqualTo(finalBook.isIssued()),
                    () -> assertThat(updatedBook.getPublishDate()).isEqualTo(finalBook.getPublishDate())
            );
            transaction.commit();
        }
    }

    @Test
    void deleteBook() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var author = session.get(Author.class, 1);
            var book = entityCreator.getTestBook(author);
            var bookId = session.save(book);
            session.flush();
            session.clear();
            book = session.get(Book.class, bookId);
            session.delete(book);
            session.flush();
            book = session.get(Book.class, bookId);
            assertThat(book).isNull();
            transaction.commit();
        }
    }
}
