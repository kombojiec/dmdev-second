package com.dmdev.app.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class HibernateTestConfig {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.5");

    static {
        container.start();
    }

    public static SessionFactory buildSessionFactory() {
        return HibernateConfig.buildConfiguration()
                .setProperty("hibernate.connection.url", container.getJdbcUrl())
                .setProperty("hibernate.connection.username", container.getUsername())
                .setProperty("hibernate.connection.password", container.getPassword())
                .configure()
                .buildSessionFactory();
    }

}
