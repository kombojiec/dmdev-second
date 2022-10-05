package com.dmdev.app.integration;

import com.dmdev.app.entity.User;
import com.dmdev.app.enums.Role;
import org.junit.jupiter.api.Test;

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
            var user1 = session.get(User.class, userId);
            assertThat(user1.getId()).isNotNull();
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
            user.setUsername(userName);
            user.setPassword(password);
            user.getInitials().setFirstName(firstName);
            user.getInitials().setSecondName(secondName);
            user.getInitials().setMiddleName(middleName);
            user.setRole(Role.LIBRARIAN);
            session.update(user);
            session.flush();
            session.clear();
            user = session.get(User.class, userId);
            transaction.commit();
            User updatedUser = user;
            assertAll(
                    () -> assertThat(updatedUser.getUsername()).isEqualTo(userName),
                    () -> assertThat(updatedUser.getPassword()).isEqualTo(password),
                    () -> assertThat(updatedUser.getInitials().getFirstName()).isEqualTo(firstName),
                    () -> assertThat(updatedUser.getInitials().getSecondName()).isEqualTo(secondName),
                    () -> assertThat(updatedUser.getInitials().getMiddleName()).isEqualTo(middleName),
                    () -> assertThat(updatedUser.getRole()).isEqualTo(Role.LIBRARIAN)
            );
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
