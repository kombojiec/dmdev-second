package com.dmdev.app.integration;

import com.dmdev.app.dao.ClientDao;
import com.dmdev.app.entity.Client;
import com.dmdev.app.enums.ClientStatus;
import com.dmdev.app.filters.ClientFilter;
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
        super();
        clientDao = ClientDao.getInstance();
    }

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
            var address = session.createQuery("select c from Client c", Client.class).list()
                    .stream().findFirst().orElseGet(entityCreator::getTestClient).getAddress();
            session.flush();
            session.clear();

            client = session.get(Client.class, clientId);
            client.getInitials().setFirstName(FIRST_NAME);
            client.getInitials().setSecondName(SECOND_NAME);
            client.getInitials().setMiddleName(MIDDLE_NAME);
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

    @Test
    void getClientsByQueryDslIsNotEmpty() {
        try(Session session = factory.openSession()) {
            var clientGraph = GraphUtil.getClientGraph(session);
            ClientFilter filter = ClientFilter.builder()
                    .firstName("John")
                    .secondName("Rambo")
                    .build();
            var clients = clientDao.getClientsByQueryDsl(session, filter, clientGraph);
            assertAll(
                    () -> assertThat(clients).isNotEmpty(),
                    () -> assertThat(clients.get(0).getInitials().getFirstName()).isEqualTo("John"),
                    () -> assertThat(clients.get(0).getInitials().getSecondName()).isEqualTo("Rambo")
            );
        }
    }

    @Test
    void getClientsByQueryDslIsEmpty() {
        try(Session session = factory.openSession()) {
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
        try(Session session = factory.openSession()) {
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
        try(Session session = factory.openSession()) {
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
