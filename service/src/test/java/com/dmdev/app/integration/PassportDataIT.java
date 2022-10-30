package com.dmdev.app.integration;

import com.dmdev.app.entity.PassportData;
import com.dmdev.app.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PassportDataIT extends AbstractIntegrationTestsClass {

    @Test
    void createPassportData() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var passportData = entityCreator.getTestPassportData();
            user.setPassportData(passportData);
            var userId = session.save(user);
            session.flush();
            session.clear();

            var createdUser = session.get(User.class, userId);

            assertThat(createdUser.getPassportData()).isEqualTo(passportData);
            transaction.commit();
        }
    }

    @Test
    void readPassportData() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var passportData = entityCreator.getTestPassportData();
            user.setPassportData(passportData);
            session.save(user);
            var passportDataId = user.getPassportData().getId();
            session.flush();
            session.clear();

            var createdPassportData = session.get(PassportData.class, passportDataId);

            assertAll(
                    () -> assertThat(createdPassportData.getId()).isEqualTo(passportDataId),
                    () -> assertThat(createdPassportData.getUser()).isEqualTo(passportData.getUser()),
                    () -> assertThat(createdPassportData.getSerial()).isEqualTo(passportData.getSerial()),
                    () -> assertThat(createdPassportData.getNumber()).isEqualTo(passportData.getNumber())
            );
            transaction.commit();
        }
    }

    @Test
    void updatePassportData() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var passportData = entityCreator.getTestPassportData();
            user.setPassportData(passportData);
            session.save(user);
            var passportDataId = user.getPassportData().getId();
            session.flush();
            session.clear();

            passportData = session.get(PassportData.class, passportDataId);
            passportData.setSerial(321);
            passportData.setNumber(987654);
            session.update(passportData);
            session.flush();
            session.clear();
            passportData = session.get(PassportData.class, passportDataId);
            var updatedPassportData = passportData;

            assertAll(
                    () -> assertThat(updatedPassportData.getSerial()).isEqualTo(321),
                    () -> assertThat(updatedPassportData.getNumber()).isEqualTo(987654)
            );
            transaction.commit();
        }
    }

    @Test
    void deletePassportData() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            User user = entityCreator.getTestUser();
            var passportData = entityCreator.getTestPassportData();
            user.setPassportData(passportData);
            var userId = session.save(user);
            var passportDataId = user.getPassportData().getId();
            session.flush();
            session.clear();

            user = session.get(User.class, userId);
            session.delete(user);
            session.flush();
            var deletedPassportData = session.get(PassportData.class, passportDataId);

            assertThat(deletedPassportData).isNull();
            transaction.commit();
        }
    }
}
