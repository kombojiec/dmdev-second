package com.dmdev.app.repositary;

import com.dmdev.app.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends AbstractCrudRepository<Integer, User> {

    public UserRepository(EntityManager em) {
        super(User.class, em);
    }

}
