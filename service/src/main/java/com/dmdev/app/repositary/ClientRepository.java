package com.dmdev.app.repositary;

import com.dmdev.app.entity.Client;

import javax.persistence.EntityManager;
import java.util.List;

public class ClientRepository extends AbstractCrudRepository<Integer, Client> {
    public ClientRepository(EntityManager em) {
        super(Client.class, em);
    }

    @Override
    public List<Client> get() {
        return em.createQuery("select c from Client c", Client.class).getResultList();
    }
}
