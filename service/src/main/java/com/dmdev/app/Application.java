package com.dmdev.app;

import com.dmdev.app.dao.ClientDao;
import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Order;
import com.dmdev.app.filters.ClientFilter;
import com.dmdev.app.util.HibernateConfig;
import org.hibernate.SessionFactory;

public class Application {

    private static final ClientDao clientDao = ClientDao.getInstance();

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateConfig.buildSessionFactory();
            var session = sessionFactory.openSession()) {

            var clientGraph = session.createEntityGraph(Client.class);
            clientGraph.addAttributeNodes("orders");
            var orderSubGraph = clientGraph.addSubgraph("orders", Order.class);
            orderSubGraph.addAttributeNodes("book");

            session.beginTransaction();
            ClientFilter filter = ClientFilter.builder()
                    .firstName("John")
                    .secondName("Rambo")
                    .build();
            var clientsByQueryDsl = clientDao.getClientsByQueryDsl(session, filter, clientGraph);
            System.out.println(clientsByQueryDsl);

            var clientsByCriteria = clientDao.getClientsByCriteria(session, filter, clientGraph);
            System.out.println(clientsByCriteria);
            session.getTransaction().commit();
        }
    }

}
