package com.dmdev.app.integration;

import com.dmdev.app.entity.Client;
import com.dmdev.app.enums.ClientStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ClientIT extends AbstractIntegrationTestsClass {

    @Test
    void createClient() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var client = entityCreator.getTestClient();
            var clientId = session.save(client);
            session.flush();
            session.clear();
            client = session.get(Client.class, clientId);
            assertThat(client).isNotNull();
            transaction.commit();
        }
    }

    @Test
    void readClient() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var client = entityCreator.getTestClient();
            var clientId = session.save(client);
            session.flush();
            session.clear();
            var createdClient = session.get(Client.class, clientId);
            assertAll(
                    () -> assertThat(createdClient.getId()).isEqualTo(clientId),
                    () -> assertThat(createdClient.getInitials().getFirstName())
                            .isEqualTo(client.getInitials().getFirstName()),
                    () -> assertThat(createdClient.getInitials().getSecondName())
                            .isEqualTo(client.getInitials().getSecondName()),
                    () -> assertThat(createdClient.getInitials().getMiddleName())
                            .isEqualTo(client.getInitials().getMiddleName()),
                    () -> assertThat(createdClient.getAddress()).isEqualTo(client.getAddress()),
                    () -> assertThat(createdClient.getStatus()).isEqualTo(client.getStatus())
            );
            transaction.commit();
        }
    }

    @Test
    void updateClient() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var client = entityCreator.getTestClient();
            var clientId = session.save(client);
            session.flush();
            session.clear();
            var address = session.get(Client.class, 1).getAddress();
            client = session.get(Client.class, clientId);
            client.getInitials().setFirstName(firstName);
            client.getInitials().setSecondName(secondName);
            client.getInitials().setMiddleName(middleName);
            client.setAddress(address);
            client.setStatus(ClientStatus.BLOCKED);
            session.update(client);
            session.flush();
            session.clear();
            var updatedClient = session.get(Client.class, clientId);
            Client finalClient = client;
            assertAll(
                    () -> assertThat(updatedClient.getInitials().getFirstName())
                            .isEqualTo(finalClient.getInitials().getFirstName()),
                    () -> assertThat(updatedClient.getInitials().getSecondName())
                            .isEqualTo(finalClient.getInitials().getSecondName()),
                    () -> assertThat(updatedClient.getInitials().getMiddleName())
                            .isEqualTo(finalClient.getInitials().getMiddleName()),
                    () -> assertThat(updatedClient.getStatus()).isEqualTo(finalClient.getStatus()),
                    () -> assertThat(updatedClient.getAddress()).isEqualTo(address)
            );
            transaction.commit();
        }
    }

    @Test
    void deleteClient() {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            var client = entityCreator.getTestClient();
            var clientId = session.save(client);
            session.flush();
            session.clear();
            client = session.get(Client.class, clientId);
            session.delete(client);
            session.flush();
            client = session.get(Client.class, clientId);
            assertThat(client).isNull();
            transaction.commit();
        }
    }
}
