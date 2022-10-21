package com.dmdev.app.integration;

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

public class UserIT extends AbstractIntegrationTestsClass {

    @Test
    void createUser() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var userId = session.save(user);
            session.flush();
            session.clear();

            var createdUser = session.get(User.class, userId);

            assertThat(createdUser.getId()).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readUser() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var userId = session.save(user);
            session.flush();
            session.clear();

            var createdUser = session.get(User.class, userId);

            assertAll(
                    () -> assertThat(createdUser.getId()).isEqualTo(userId),
                    () -> assertThat(createdUser.getUsername()).isEqualTo(user.getUsername()),
                    () -> assertThat(createdUser.getPassword()).isEqualTo(user.getPassword()),
                    () -> assertThat(createdUser.getInitials().getFirstName()).isEqualTo(user.getInitials().getFirstName()),
                    () -> assertThat(createdUser.getInitials().getSecondName()).isEqualTo(user.getInitials().getSecondName()),
                    () -> assertThat(createdUser.getInitials().getMiddleName()).isEqualTo(user.getInitials().getMiddleName()),
                    () -> assertThat(createdUser.getRole()).isEqualTo(user.getRole())
            );
            transaction.commit();
        }
    }

    @Test
    void updateUser() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var userId = session.save(user);
            session.flush();
            session.clear();

            user = session.get(User.class, userId);
            user.setUsername(USER_NAME);
            user.setPassword(PASSWORD);
            user.getInitials().setFirstName(FIRST_NAME);
            user.getInitials().setSecondName(SECOND_NAME);
            user.getInitials().setMiddleName(MIDDLE_NAME);
            user.setRole(Role.LIBRARIAN);
            session.update(user);
            session.flush();
            session.clear();
            user = session.get(User.class, userId);
            User updatedUser = user;

            assertAll(
                    () -> assertThat(updatedUser.getUsername()).isEqualTo(USER_NAME),
                    () -> assertThat(updatedUser.getPassword()).isEqualTo(PASSWORD),
                    () -> assertThat(updatedUser.getInitials().getFirstName()).isEqualTo(FIRST_NAME),
                    () -> assertThat(updatedUser.getInitials().getSecondName()).isEqualTo(SECOND_NAME),
                    () -> assertThat(updatedUser.getInitials().getMiddleName()).isEqualTo(MIDDLE_NAME),
                    () -> assertThat(updatedUser.getRole()).isEqualTo(Role.LIBRARIAN)
            );
            transaction.commit();
        }
    }

    @Test
    void deleteUser() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var userId = session.save(user);
            session.flush();
            session.clear();

            user = session.get(User.class, userId);
            session.delete(user);
            session.flush();
            var deletedUser = session.get(User.class, user.getId());

            assertThat(deletedUser).isNull();
            transaction.commit();
        }
    }
}
