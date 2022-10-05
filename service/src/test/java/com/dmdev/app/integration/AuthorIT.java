package com.dmdev.app.integration;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Initials;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthorIT extends AbstractIntegrationTestsClass {

    @Test
    void createAuthor() {
        var author = entityCreator.getTestAuthor();
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var authorId = session.save(author);
            session.flush();
            session.clear();
            var createdAuthor = session.get(Author.class, authorId);
            assertThat(createdAuthor).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readAuthor() {
        var author = entityCreator.getTestAuthor();
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var authorId = session.save(author);
            session.flush();
            session.clear();
            var createdAuthor = session.get(Author.class, authorId);
            assertAll(
                    () -> assertThat(createdAuthor.getId()).isEqualTo(authorId),
                    () -> assertThat(createdAuthor.getInitials().getFirstName())
                            .isEqualTo(author.getInitials().getFirstName()),
                    () -> assertThat(createdAuthor.getInitials().getSecondName())
                            .isEqualTo(author.getInitials().getSecondName()),
                    () -> assertThat(createdAuthor.getInitials().getMiddleName())
                            .isEqualTo(author.getInitials().getMiddleName())
            );
            transaction.commit();
        }
    }

    @Test
    void updateAuthor() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var author = entityCreator.getTestAuthor();
            var authorId = session.save(author);
            session.flush();
            session.clear();
            author = session.get(Author.class, authorId);
            author.setInitials(Initials.builder()
                    .firstName(firstName)
                    .middleName(middleName)
                    .secondName(secondName)
                    .build());
            session.update(author);
            session.flush();
            session.clear();
            author = session.get(Author.class, authorId);
            Author createdAuthor = author;
            assertAll(
                    () -> assertThat(createdAuthor.getInitials().getFirstName()).isEqualTo(firstName),
                    () -> assertThat(createdAuthor.getInitials().getSecondName()).isEqualTo(secondName),
                    () -> assertThat(createdAuthor.getInitials().getMiddleName()).isEqualTo(middleName)
            );
            transaction.commit();
        }
    }

    @Test
    void deleteAuthor() {
        try (var session = factory.openSession()) {
            var author = entityCreator.getTestAuthor();
            var transaction = session.beginTransaction();
            var authorId = session.save(author);
            session.flush();
            session.clear();
            author = session.get(Author.class, authorId);
            session.delete(author);
            session.flush();
            author = session.get(Author.class, authorId);
            assertThat(author).isNull();
            transaction.commit();
        }
    }
}
