package com.dmdev.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.dmdev.app")
@Configuration
public class ApplicationConfig {

//    @Bean
//    public SessionFactory sessionFactory() {
//        return HibernateConfig.buildSessionFactory();
//    }
//
//    @Bean
//    public EntityManager entityManager() {
//        var factory = sessionFactory();
//        return (EntityManager) Proxy.newProxyInstance(factory.getClass().getClassLoader(), new Class[]{Session.class},
//                (proxy, method, args) -> method.invoke(factory.getCurrentSession(), args));
//    }

}
