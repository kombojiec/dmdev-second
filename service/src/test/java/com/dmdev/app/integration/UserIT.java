package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.entity.User;
import com.dmdev.app.enums.Role;
import org.junit.jupiter.api.Test;

import static com.dmdev.app.util.TextConstants.FIRST_NAME;
import static com.dmdev.app.util.TextConstants.MIDDLE_NAME;
import static com.dmdev.app.util.TextConstants.PASSWORD;
import static com.dmdev.app.util.TextConstants.SECOND_NAME;
import static com.dmdev.app.util.TextConstants.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
public class UserIT extends AbstractIntegrationTestsClass {
    
    @Test
    void createUser() {
        User user = userRepository.save(entityCreator.getTestUser());
        em.flush();
        em.clear();

        var createdUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    void readUser() {
        var user = userRepository.save(entityCreator.getTestUser());
        em.flush();
        em.clear();

        User createdUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdUser.getId()).isNotNull(),
                () -> assertThat(createdUser.getUsername()).isEqualTo(user.getUsername()),
                () -> assertThat(createdUser.getPassword()).isEqualTo(user.getPassword()),
                () -> assertThat(createdUser.getInitials().getFirstName()).isEqualTo(user.getInitials().getFirstName()),
                () -> assertThat(createdUser.getInitials().getSecondName()).isEqualTo(user.getInitials().getSecondName()),
                () -> assertThat(createdUser.getInitials().getMiddleName()).isEqualTo(user.getInitials().getMiddleName()),
                () -> assertThat(createdUser.getRole()).isEqualTo(user.getRole())
        );
    }

    @Test
    void updateUser() {
        User user = userRepository.save(entityCreator.getTestUser());
        em.flush();
        em.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        user.getInitials().setFirstName(FIRST_NAME);
        user.getInitials().setSecondName(SECOND_NAME);
        user.getInitials().setMiddleName(MIDDLE_NAME);
        user.setRole(Role.LIBRARIAN);
        userRepository.update(user);
        em.flush();
        em.clear();
        var updatedUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedUser.getUsername()).isEqualTo(USER_NAME),
                () -> assertThat(updatedUser.getPassword()).isEqualTo(PASSWORD),
                () -> assertThat(updatedUser.getInitials().getFirstName()).isEqualTo(FIRST_NAME),
                () -> assertThat(updatedUser.getInitials().getSecondName()).isEqualTo(SECOND_NAME),
                () -> assertThat(updatedUser.getInitials().getMiddleName()).isEqualTo(MIDDLE_NAME),
                () -> assertThat(updatedUser.getRole()).isEqualTo(Role.LIBRARIAN)
        );
    }

    @Test
    void deleteUser() {
        User user = userRepository.save(entityCreator.getTestUser());
        em.flush();
        em.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
        var deletedUser = userRepository.getById(user.getId());

        assertThat(deletedUser.isEmpty()).isTrue();
    }
}
