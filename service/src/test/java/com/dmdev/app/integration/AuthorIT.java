package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.entity.Initials;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static com.dmdev.app.util.TextConstants.FIRST_NAME;
import static com.dmdev.app.util.TextConstants.MIDDLE_NAME;
import static com.dmdev.app.util.TextConstants.SECOND_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
@RequiredArgsConstructor
public class AuthorIT extends AbstractIntegrationTestsClass {

    @Test
    void createAuthor() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        em.flush();
        em.clear();

        var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdAuthor).isNotNull();
    }

    @Test
    void readAuthor() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        em.flush();
        em.clear();

        var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdAuthor.getInitials().getFirstName())
                        .isEqualTo(author.getInitials().getFirstName()),
                () -> assertThat(createdAuthor.getInitials().getSecondName())
                        .isEqualTo(author.getInitials().getSecondName()),
                () -> assertThat(createdAuthor.getInitials().getMiddleName())
                        .isEqualTo(author.getInitials().getMiddleName())
        );
    }

    @Test
    void updateAuthor() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        em.flush();
        em.clear();

        author.setInitials(Initials.builder()
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .secondName(SECOND_NAME)
                .build());
        authorRepository.update(author);
        em.flush();
        em.clear();
        var createdAuthor = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdAuthor.getInitials()).isEqualTo(author.getInitials());
    }

    @Test
    void deleteAuthor() {
        var author = authorRepository.save(entityCreator.getTestAuthor());
        em.flush();
        em.clear();

        author = authorRepository.getById(author.getId()).orElseThrow(IllegalArgumentException::new);
        authorRepository.delete(author);
        var deletedAuthor = authorRepository.getById(author.getId());

        assertThat(deletedAuthor.isEmpty()).isTrue();
    }
}
