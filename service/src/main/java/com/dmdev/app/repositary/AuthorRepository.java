package com.dmdev.app.repositary;

import com.dmdev.app.entity.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AuthorRepository extends AbstractCrudRepository<Integer, Author> {

    public AuthorRepository(EntityManager em) {
        super(Author.class, em);
    }

}
