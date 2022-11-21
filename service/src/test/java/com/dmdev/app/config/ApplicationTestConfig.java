package com.dmdev.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages = "com.dmdev.app",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ApplicationConfig.class))
@Configuration
public class ApplicationTestConfig {

//    @Bean
//    public SessionFactory factory() {
//        return HibernateTestConfig.buildSessionFactory();
//    }
//
//    @Bean
//    public EntityManager session() {
//        var factory = factory();
//        return (EntityManager) Proxy.newProxyInstance(factory.getClass().getClassLoader(), new Class[]{Session.class},
//                (proxy, method, args) -> method.invoke(factory.getCurrentSession(), args));
//    }

}
