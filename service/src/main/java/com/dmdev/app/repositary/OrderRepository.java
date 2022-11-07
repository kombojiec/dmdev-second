package com.dmdev.app.repositary;

import com.dmdev.app.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository extends AbstractCrudRepository<Integer, Order> {

    public OrderRepository(EntityManager em) {
        super(Order.class, em);
    }

}
