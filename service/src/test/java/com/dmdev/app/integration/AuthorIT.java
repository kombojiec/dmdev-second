package com.dmdev.app.integration;

import com.dmdev.app.entity.Initials;
import com.dmdev.app.repositary.AuthorRepository;
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
            AuthorRepository authorRepository = new AuthorRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

            assertThat(createdAuthor).isNotNull();
            transaction.rollback();
        }
    }

    @Test
    void readAuthor() {
        try (var session = factory.openSession()) {
            AuthorRepository authorRepository = new AuthorRepository(session);
            var author = authorRepository.save(entityCreator.getTestAuthor());
            var transaction = session.beginTransaction();
            session.flush();
            session.clear();

            var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(createdAuthor.getInitials().getFirstName())
                            .isEqualTo(author.getInitials().getFirstName()),
                    () -> assertThat(createdAuthor.getInitials().getSecondName())
                            .isEqualTo(author.getInitials().getSecondName()),
                    () -> assertThat(createdAuthor.getInitials().getMiddleName())
                            .isEqualTo(author.getInitials().getMiddleName())
            );
            transaction.rollback();
        }
    }

    @Test
    void updateAuthor() {
        try (var session = factory.openSession()) {
            AuthorRepository authorRepository = new AuthorRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            author.setInitials(Initials.builder()
                    .firstName(FIRST_NAME)
                    .middleName(MIDDLE_NAME)
                    .secondName(SECOND_NAME)
                    .build());
            authorRepository.update(author);
            session.flush();
            session.clear();
            var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

            assertThat(createdAuthor.getInitials()).isEqualTo(author.getInitials());
            transaction.rollback();
        }
    }

    @Test
    void deleteAuthor() {
        try (var session = factory.openSession()) {
            AuthorRepository authorRepository = new AuthorRepository(session);
            var transaction = session.beginTransaction();
            var author = authorRepository.save(entityCreator.getTestAuthor());
            session.flush();
            session.clear();

            authorRepository.delete(author.getId());
            var deletedAuthor = authorRepository.getById(author.getId());

            assertThat(deletedAuthor.isEmpty()).isTrue();
            transaction.commit();
        }
    }
}
