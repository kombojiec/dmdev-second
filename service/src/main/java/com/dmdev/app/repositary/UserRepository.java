package com.dmdev.app.repositary;

import com.dmdev.app.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepository extends AbstractCrudRepository<Integer, User> {
    public UserRepository(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public List<User> get() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }
}
