package com.dmdev.app.config;

import com.dmdev.app.util.HibernateTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@ComponentScan(basePackages = "com.dmdev.app")
@Configuration
public class ApplicationTestConfig {

    @Bean
    public SessionFactory factory() {
        return HibernateTestConfig.buildSessionFactory();
    }

    @Bean
    public EntityManager session() {
        var factory = factory();
        return (EntityManager) Proxy.newProxyInstance(factory.getClass().getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(factory.getCurrentSession(), args));
    }

}
