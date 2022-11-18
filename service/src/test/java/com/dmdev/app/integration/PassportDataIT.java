package com.dmdev.app.integration;

import com.dmdev.app.config.ApplicationTestConfig;
import com.dmdev.app.entity.User;
import com.dmdev.app.repositary.PassportDataRepository;
import com.dmdev.app.repositary.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PassportDataIT extends AbstractIntegrationTestsClass {

    @Disabled
    @Test
    void createPassportData() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var userRepository = context.getBean(UserRepository.class);
        var transaction = session.getTransaction();
        transaction.begin();
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        user = userRepository.save(user);
        session.flush();
        session.clear();

        var createdUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdUser.getPassportData()).isEqualTo(passportData);
        transaction.commit();
    }

    @Disabled
    @Test
    void readPassportData() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var transaction = session.getTransaction();
        var userRepository = context.getBean(UserRepository.class);
        var passportDataRepository = context.getBean(PassportDataRepository.class);
        transaction.begin();
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        session.flush();
        session.clear();

        var createdPassportData = passportDataRepository.getById(passportDataId)
                .orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdPassportData.getId()).isEqualTo(passportDataId),
                () -> assertThat(createdPassportData.getUser()).isEqualTo(passportData.getUser()),
                () -> assertThat(createdPassportData.getSerial()).isEqualTo(passportData.getSerial()),
                () -> assertThat(createdPassportData.getNumber()).isEqualTo(passportData.getNumber())
        );
        transaction.commit();
    }

    @Disabled
    @Test
    void updatePassportData() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var transaction = session.getTransaction();
        var userRepository = context.getBean(UserRepository.class);
        var passportDataRepository = context.getBean(PassportDataRepository.class);
        transaction.begin();
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        session.flush();
        session.clear();

        passportData = passportDataRepository.getById(passportDataId).orElseThrow(IllegalArgumentException::new);
        passportData.setSerial(321);
        passportData.setNumber(987654);
        passportDataRepository.update(passportData);
        session.flush();
        session.clear();
        var updatedPassportData = passportDataRepository.getById(passportDataId)
                .orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedPassportData.getSerial()).isEqualTo(321),
                () -> assertThat(updatedPassportData.getNumber()).isEqualTo(987654)
        );
        transaction.commit();
    }

    @Disabled
    @Test
    void deletePassportData() {
        var context = new AnnotationConfigApplicationContext(ApplicationTestConfig.class);
        var session = context.getBean(EntityManager.class);
        var transaction = session.getTransaction();
        var userRepository = context.getBean(UserRepository.class);
        var passportDataRepository = context.getBean(PassportDataRepository.class);
        transaction.begin();
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        user = userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        session.flush();
        session.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
        session.flush();
        var deletedPassportData = userRepository.getById(passportDataId);

        assertThat(deletedPassportData.isEmpty()).isTrue();
        transaction.commit();
    }
}
