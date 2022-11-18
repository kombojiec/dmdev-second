package com.dmdev.app.integration;

import com.dmdev.app.config.ApplicationTestConfig;
import com.dmdev.app.enums.Genre;
import com.dmdev.app.repositary.AuthorRepository;
import com.dmdev.app.repositary.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookIT extends AbstractIntegrationTestsClass {

    @Test
    void createBook() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var bookRepository = context.getBean(BookRepository.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        session.flush();
        session.clear();

        var createdBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdBook).isNotNull();
        transaction.rollback();
    }

    @Test
    void readBook() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var bookRepository = context.getBean(BookRepository.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
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

    @Test
    void updateBook() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var bookRepository = context.getBean(BookRepository.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
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

    @Test
    void deleteBook() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var bookRepository = context.getBean(BookRepository.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        session.flush();
        session.clear();

        bookRepository.delete(book);
        var deletedBook = bookRepository.getById(book.getId());

        assertThat(deletedBook.isEmpty()).isTrue();
        transaction.rollback();
    }
}
