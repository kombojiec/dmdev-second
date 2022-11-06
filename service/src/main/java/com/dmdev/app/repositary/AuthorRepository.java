package com.dmdev.app.repositary;

import com.dmdev.app.entity.Author;

import javax.persistence.EntityManager;
import java.util.List;

public class AuthorRepository extends AbstractCrudRepository<Integer, Author> {

    public AuthorRepository(EntityManager em) {
        super(Author.class, em);
    }

    @Override
    public List<Author> get() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
