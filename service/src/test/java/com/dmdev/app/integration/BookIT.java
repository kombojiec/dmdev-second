package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.enums.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
@IT
public class BookIT extends AbstractIntegrationTestsClass {

    @Test
    void createBook() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        em.flush();
        em.clear();

        var createdBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdBook).isNotNull();
    }

    @Test
    void readBook() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        em.flush();
        em.clear();

        var createdBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdBook.getId()).isNotNull(),
                () -> assertThat(createdBook.getAuthor()).isEqualTo(author),
                () -> assertThat(createdBook.getGenre()).isEqualTo(book.getGenre()),
                () -> assertThat(createdBook.getName()).isEqualTo(book.getName()),
                () -> assertThat(createdBook.getPageSize()).isEqualTo(book.getPageSize()),
                () -> assertThat(createdBook.getPublishDate()).isEqualTo(book.getPublishDate())
        );
    }

    @Test
    void updateBook() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        var newAuthor = authorRepository.save(entityCreator.getTestAuthor());
        em.flush();
        em.clear();

        book.setName("new name");
        book.setAuthor(newAuthor);
        book.setGenre(Genre.LYRIC);
        book.setPageSize(333);
        book.setIssued(true);
        book.setPublishDate(LocalDate.of(2222, 2, 22));
        bookRepository.update(book);
        em.flush();
        em.clear();
        var updatedBook = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedBook.getName()).isEqualTo(book.getName()),
                () -> assertThat(updatedBook.getAuthor()).isEqualTo(book.getAuthor()),
                () -> assertThat(updatedBook.getGenre()).isEqualTo(book.getGenre()),
                () -> assertThat(updatedBook.getPageSize()).isEqualTo(book.getPageSize()),
                () -> assertThat(updatedBook.isIssued()).isEqualTo(book.isIssued()),
                () -> assertThat(updatedBook.getPublishDate()).isEqualTo(book.getPublishDate())
        );
    }

    @Test
    void deleteBook() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        var book = bookRepository.save(entityCreator.getTestBook(author));
        em.flush();
        em.clear();

        book = bookRepository.getById(book.getId()).orElseThrow(IllegalArgumentException::new);
        bookRepository.delete(book);
        var deletedBook = bookRepository.getById(book.getId());

        assertThat(deletedBook.isEmpty()).isTrue();
    }
}
