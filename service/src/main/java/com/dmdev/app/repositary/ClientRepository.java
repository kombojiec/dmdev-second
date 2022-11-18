package com.dmdev.app.repositary;

import com.dmdev.app.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ClientRepository extends AbstractCrudRepository<Integer, Client> {

    public ClientRepository(EntityManager em) {
        super(Client.class, em);
    }

}
