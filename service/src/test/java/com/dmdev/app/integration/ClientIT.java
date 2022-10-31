package com.dmdev.app.integration;

import com.dmdev.app.repositary.ClientDao;
import com.dmdev.app.entity.Client;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.filters.ClientFilter;
import com.dmdev.app.repositary.ClientRepository;
import com.dmdev.app.util.GraphUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static com.dmdev.app.util.TextConstants.FIRST_NAME;
import static com.dmdev.app.util.TextConstants.MIDDLE_NAME;
import static com.dmdev.app.util.TextConstants.SECOND_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ClientIT extends AbstractIntegrationTestsClass {

    private final ClientDao clientDao;

    public ClientIT() {
        clientDao = ClientDao.getInstance();
    }

    @Test
    void createClient() {
        try (var session = factory.openSession()) {
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var client = clientRepository.save(entityCreator.getTestClient());
            session.flush();
            session.clear();

            var createdClient = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);

            assertThat(createdClient).isNotNull();
            transaction.rollback();
        }
    }

    @Test
    void readClient() {
        try (var session = factory.openSession()) {
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var client = clientRepository.save(entityCreator.getTestClient());
            session.flush();
            session.clear();

            var createdClient = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);

            assertAll(
                    () -> assertThat(createdClient.getInitials()).isEqualTo(client.getInitials()),
                    () -> assertThat(createdClient.getAddress()).isEqualTo(client.getAddress()),
                    () -> assertThat(createdClient.getStatus()).isEqualTo(client.getStatus())
            );
            transaction.rollback();
        }
    }

    @Test
    void updateClient() {
        try (var session = factory.openSession()) {
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var client = clientRepository.save(entityCreator.getTestClient());
            var address =
                    entityCreator.getAddress("New York", "USA", "Wall Street", "123");
            session.flush();
            session.clear();

            client = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);
            client.getInitials().setFirstName(FIRST_NAME);
            client.getInitials().setSecondName(SECOND_NAME);
            client.getInitials().setMiddleName(MIDDLE_NAME);
            client.setAddress(address);
            client.setStatus(ClientStatus.BLOCKED);
            clientRepository.update(client);
            session.flush();
            session.clear();
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
            transaction.rollback();
        }
    }

    @Test
    void deleteClient() {
        try (var session = factory.openSession()) {
            var clientRepository = new ClientRepository(session);
            var transaction = session.beginTransaction();
            var client = clientRepository.save(entityCreator.getTestClient());
            session.flush();
            session.clear();

            client = clientRepository.getById(client.getId()).orElseThrow(IllegalArgumentException::new);
            session.delete(client);
            session.flush();
            var deletedClient = clientRepository.getById(client.getId());

            assertThat(deletedClient.isEmpty()).isTrue();
            transaction.rollback();
        }
    }

    @Test
    void getClientsByQueryDslIsNotEmpty() {
        try (Session session = factory.openSession()) {
            var clientGraph = GraphUtil.getClientGraph(session);
            ClientFilter filter = ClientFilter.builder()
                    .firstName("John")
                    .secondName("Rambo")
                    .build();
            var clients = clientDao.getClientsByQueryDsl(session, filter, clientGraph);
            assertThat(clients).isNotEmpty();
            assertThat(clients.get(0).getInitials().getFirstName()).isEqualTo("John");
            assertThat(clients.get(0).getInitials().getSecondName()).isEqualTo("Rambo");
        }
    }

    @Test
    void getClientsByQueryDslIsEmpty() {
        try (Session session = factory.openSession()) {
            var clientGraph = GraphUtil.getClientGraph(session);
            ClientFilter filter = ClientFilter.builder()
                    .firstName("Vasya")
                    .secondName("Pupkin")
                    .build();
            var clients = clientDao.getClientsByQueryDsl(session, filter, clientGraph);
            assertThat(clients).isEmpty();
        }
    }

    @Test
    void getClientsByCriteriaIsNotEmpty() {
        try (Session session = factory.openSession()) {
            var clientGraph = GraphUtil.getClientGraph(session);
            ClientFilter filter = ClientFilter.builder()
                    .firstName("John")
                    .secondName("Rambo")
                    .build();
            var clients = clientDao.getClientsByCriteria(session, filter, clientGraph);
            assertAll(
                    () -> assertThat(clients).isNotEmpty(),
                    () -> assertThat(clients.get(0).getInitials().getFirstName()).isEqualTo("John"),
                    () -> assertThat(clients.get(0).getInitials().getSecondName()).isEqualTo("Rambo")
            );
        }
    }

    @Test
    void getClientsByCriteriaIsEmpty() {
        try (Session session = factory.openSession()) {
            var clientGraph = GraphUtil.getClientGraph(session);
            ClientFilter filter = ClientFilter.builder()
                    .firstName("Vasya")
                    .secondName("Pupkin")
                    .build();
            var clients = clientDao.getClientsByCriteria(session, filter, clientGraph);
            assertThat(clients).isEmpty();
        }
    }

}
