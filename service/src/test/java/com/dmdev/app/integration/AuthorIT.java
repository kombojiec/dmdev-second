package com.dmdev.app.integration;

import com.dmdev.app.entity.Author;
import com.dmdev.app.entity.Initials;
import org.junit.jupiter.api.Test;

import static com.dmdev.app.util.TextConstants.FIRST_NAME;
import static com.dmdev.app.util.TextConstants.MIDDLE_NAME;
import static com.dmdev.app.util.TextConstants.SECOND_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthorIT extends AbstractIntegrationTestsClass {

    @Test
    void createAuthor() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var authorId = session.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            var createdAuthor = session.get(Author.class, authorId);

            assertThat(createdAuthor).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readAuthor() {
        try (var session = factory.openSession()) {
            var author = entityCreator.getTestAuthor();
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
                    .firstName(FIRST_NAME)
                    .middleName(MIDDLE_NAME)
                    .secondName(SECOND_NAME)
                    .build());
            session.update(author);
            session.flush();
            session.clear();
            author = session.get(Author.class, authorId);
            Author createdAuthor = author;

            assertThat(createdAuthor.getInitials()).isEqualTo(author.getInitials());
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
