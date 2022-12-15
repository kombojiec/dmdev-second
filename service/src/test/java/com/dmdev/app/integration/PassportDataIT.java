package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.entity.User;
import com.dmdev.app.repositary.PassportDataRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
@IT
public class PassportDataIT extends AbstractIntegrationTestsClass {

    private final PassportDataRepository passportDataRepository;

    @Test
    void createPassportData() {
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        user = userRepository.save(user);
        em.flush();
        em.clear();

        var createdUser = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdUser.getPassportData()).isEqualTo(passportData);
    }

    @Test
    void readPassportData() {
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        em.flush();
        em.clear();

        var createdPassportData = passportDataRepository.getById(passportDataId)
                .orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdPassportData.getId()).isEqualTo(passportDataId),
                () -> assertThat(createdPassportData.getUser()).isEqualTo(passportData.getUser()),
                () -> assertThat(createdPassportData.getSerial()).isEqualTo(passportData.getSerial()),
                () -> assertThat(createdPassportData.getNumber()).isEqualTo(passportData.getNumber())
        );
    }

    @Test
    void updatePassportData() {
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        em.flush();
        em.clear();

        passportData = passportDataRepository.getById(passportDataId).orElseThrow(IllegalArgumentException::new);
        passportData.setSerial(321);
        passportData.setNumber(987654);
        passportDataRepository.update(passportData);
        em.flush();
        em.clear();
        var updatedPassportData = passportDataRepository.getById(passportDataId)
                .orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(updatedPassportData.getSerial()).isEqualTo(321),
                () -> assertThat(updatedPassportData.getNumber()).isEqualTo(987654)
        );
    }

    @Test
    void deletePassportData() {
        User user = entityCreator.getTestUser();
        var passportData = entityCreator.getTestPassportData();
        user.setPassportData(passportData);
        user = userRepository.save(user);
        var passportDataId = user.getPassportData().getId();
        em.flush();
        em.clear();

        user = userRepository.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
        em.flush();
        em.clear();
        var deletedPassportData = passportDataRepository.getById(passportDataId);

        assertThat(deletedPassportData.isEmpty()).isTrue();
    }
}
