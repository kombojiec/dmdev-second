package com.dmdev.app.integration;

import com.dmdev.app.enums.Genre;
import com.dmdev.app.repositary.AuthorRepository;
import com.dmdev.app.repositary.BookRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookIT extends AbstractIntegrationTestsClass {

    @Test
    void createBook() {
        try (var session = factory.openSession()) {
            BookRepository bookRepository = new BookRepository(session);
            var authorRepository = new AuthorRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            var book = bookRepository.save(entityCreator.getTestBook(author));
            session.flush();
            session.clear();

            var createdBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

            assertThat(createdBook).isNotNull();
            transaction.rollback();
        }
    }

    @Test
    void readBook() {
        try (var session = factory.openSession()) {
            var authorRepository = new AuthorRepository(session);
            var bookRepository = new BookRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            var book = bookRepository.save(entityCreator.getTestBook(author));
            session.flush();
            session.clear();

            var createdBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(createdBook.getId()).isNotNull(),
                    () -> assertThat(createdBook.getAuthor()).isEqualTo(author),
                    () -> assertThat(createdBook.getGenre()).isEqualTo(book.getGenre()),
                    () -> assertThat(createdBook.getName()).isEqualTo(book.getName()),
                    () -> assertThat(createdBook.getPageSize()).isEqualTo(book.getPageSize()),
                    () -> assertThat(createdBook.getPublishDate()).isEqualTo(book.getPublishDate())
            );
            transaction.rollback();
        }
    }

    @Test
    void updateBook() {
        try (var session = factory.openSession()) {
            var authorRepository = new AuthorRepository(session);
            var bookRepository = new BookRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            var book = bookRepository.save(entityCreator.getTestBook(author));
            var newAuthor = authorRepository.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            book.setName("new name");
            book.setAuthor(newAuthor);
            book.setGenre(Genre.LYRIC);
            book.setPageSize(333);
            book.setIssued(true);
            book.setPublishDate(LocalDate.of(2222, 2, 22));
            bookRepository.update(book);
            session.flush();
            session.clear();
            var updatedBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(updatedBook.getName()).isEqualTo(book.getName()),
                    () -> assertThat(updatedBook.getAuthor()).isEqualTo(book.getAuthor()),
                    () -> assertThat(updatedBook.getGenre()).isEqualTo(book.getGenre()),
                    () -> assertThat(updatedBook.getPageSize()).isEqualTo(book.getPageSize()),
                    () -> assertThat(updatedBook.isIssued()).isEqualTo(book.isIssued()),
                    () -> assertThat(updatedBook.getPublishDate()).isEqualTo(book.getPublishDate())
            );
            transaction.rollback();
        }
    }

    @Test
    void deleteBook() {
        try (var session = factory.openSession()) {
            var bookRepository = new BookRepository(session);
            var authorRepository = new AuthorRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            var book = bookRepository.save(entityCreator.getTestBook(author));
            session.flush();
            session.clear();

            bookRepository.delete(book.getId());
            var deletedBook = bookRepository.getById(book.getId());

            assertThat(deletedBook.isEmpty()).isTrue();
            transaction.rollback();
        }
    }
}
