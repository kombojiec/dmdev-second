package com.dmdev.app.integration;

import com.dmdev.app.anotations.IT;
import com.dmdev.app.entity.Client;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.filters.ClientFilter;
import org.junit.jupiter.api.Test;

import static com.dmdev.app.util.TextConstants.FIRST_NAME;
import static com.dmdev.app.util.TextConstants.MIDDLE_NAME;
import static com.dmdev.app.util.TextConstants.SECOND_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
public class ClientIT extends AbstractIntegrationTestsClass {

    @Test
    void createClient() {
        var client = clientRepository.save(entityCreator.getTestClient());
        em.flush();
        em.clear();

        var createdClient = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(createdClient).isNotNull();
    }

    @Test
    void readClient() {
        var client = clientRepository.save(entityCreator.getTestClient());
        em.flush();
        em.clear();

        var createdClient = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);

        assertAll(
                () -> assertThat(createdClient.getInitials()).isEqualTo(client.getInitials()),
                () -> assertThat(createdClient.getAddress()).isEqualTo(client.getAddress()),
                () -> assertThat(createdClient.getStatus()).isEqualTo(client.getStatus())
        );
    }

    @Test
    void updateClient() {
        var client = clientRepository.save(entityCreator.getTestClient());
        var address =
                entityCreator.getAddress("New York", "USA", "Wall Street", "123");
        em.flush();
        em.clear();

        client = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);
        client.getInitials().setFirstName(FIRST_NAME);
        client.getInitials().setSecondName(SECOND_NAME);
        client.getInitials().setMiddleName(MIDDLE_NAME);
        client.setAddress(address);
        client.setStatus(ClientStatus.BLOCKED);
        clientRepository.update(client);
        em.flush();
        em.clear();
        var updatedClient = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);
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
    }

    @Test
    void deleteClient() {
        var client = clientRepository.save(entityCreator.getTestClient());
        em.flush();
        em.clear();

        client = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);
        clientRepository.delete(client);
        var deletedClient = clientRepository.getById(client.getId());

        assertThat(deletedClient.isEmpty()).isTrue();
    }

    @Test
    void getClientsByQueryDslIsNotEmpty() {
        var clientGraph = em.createEntityGraph(Client.class);
        ClientFilter filter = ClientFilter.builder()
                .firstName("John")
                .secondName("Rambo")
                .build();
        var clients = clientRepository.getClientsByQueryDsl(filter, clientGraph);
        assertThat(clients).isNotEmpty();
        assertThat(clients.get(0).getInitials().getFirstName()).isEqualTo("John");
        assertThat(clients.get(0).getInitials().getSecondName()).isEqualTo("Rambo");
    }

    @Test
    void getClientsByQueryDslIsEmpty() {
        var clientGraph = em.createEntityGraph(Client.class);
        ClientFilter filter = ClientFilter.builder()
                .firstName("Vasya")
                .secondName("Pupkin")
                .build();
        var clients = clientRepository.getClientsByQueryDsl(filter, clientGraph);
        assertThat(clients).isEmpty();
    }

    @Test
    void getClientsByCriteriaIsNotEmpty() {
        var clientGraph = em.createEntityGraph(Client.class);
        ClientFilter filter = ClientFilter.builder()
                .firstName("John")
                .secondName("Rambo")
                .build();
        var clients = clientRepository.getClientsByCriteria(filter, clientGraph);
        assertAll(
                () -> assertThat(clients).isNotEmpty(),
                () -> assertThat(clients.get(0).getInitials().getFirstName()).isEqualTo("John"),
                () -> assertThat(clients.get(0).getInitials().getSecondName()).isEqualTo("Rambo")
        );
    }

    @Test
    void getClientsByCriteriaIsEmpty() {
        var clientGraph = em.createEntityGraph(Client.class);
        ClientFilter filter = ClientFilter.builder()
                .firstName("Vasya")
                .secondName("Pupkin")
                .build();
        var clients = clientRepository.getClientsByCriteria(filter, clientGraph);
        assertThat(clients).isEmpty();
    }

}
