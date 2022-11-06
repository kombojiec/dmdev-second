package com.dmdev.app.repositary;

import com.dmdev.app.entity.Order;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderRepository extends AbstractCrudRepository<Integer, Order> {
    public OrderRepository(EntityManager em) {
        super(Order.class, em);
    }

    @Override
    public List<Order> get() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }
}
