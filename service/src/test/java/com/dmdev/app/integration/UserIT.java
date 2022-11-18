package com.dmdev.app.integration;

import com.dmdev.app.config.ApplicationTestConfig;
import com.dmdev.app.entity.User;
import com.dmdev.app.enums.Role;
import com.dmdev.app.repositary.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

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
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var userRepository = context.getBean(UserRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        User user = userRepository.save(entityCreator.getTestUser());
        session.flush();
        session.clear();

        var createdUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdUser.getId()).isNotNull();
        transaction.rollback();
    }

    @Test
    void readUser() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var userRepository = context.getBean(UserRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        var user = userRepository.save(entityCreator.getTestUser());
        session.flush();
        session.clear();

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
        transaction.rollback();
    }

    @Test
    void updateUser() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var userRepository = context.getBean(UserRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        User user = userRepository.save(entityCreator.getTestUser());
        session.flush();
        session.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        user.getInitials().setFirstName(FIRST_NAME);
        user.getInitials().setSecondName(SECOND_NAME);
        user.getInitials().setMiddleName(MIDDLE_NAME);
        user.setRole(Role.LIBRARIAN);
        userRepository.update(user);
        session.flush();
        session.clear();
        var updatedUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedUser.getUsername()).isEqualTo(USER_NAME),
                () -> assertThat(updatedUser.getPassword()).isEqualTo(PASSWORD),
                () -> assertThat(updatedUser.getInitials().getFirstName()).isEqualTo(FIRST_NAME),
                () -> assertThat(updatedUser.getInitials().getSecondName()).isEqualTo(SECOND_NAME),
                () -> assertThat(updatedUser.getInitials().getMiddleName()).isEqualTo(MIDDLE_NAME),
                () -> assertThat(updatedUser.getRole()).isEqualTo(Role.LIBRARIAN)
        );
        transaction.rollback();
    }

    @Test
    void deleteUser() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var userRepository = context.getBean(UserRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        User user = userRepository.save(entityCreator.getTestUser());
        session.flush();
        session.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
        var deletedUser = userRepository.getById(user.getId());

        assertThat(deletedUser.isEmpty()).isTrue();
        transaction.rollback();
    }
}
