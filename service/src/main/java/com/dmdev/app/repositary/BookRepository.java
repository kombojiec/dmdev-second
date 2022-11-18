package com.dmdev.app.repositary;

import com.dmdev.app.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BookRepository extends AbstractCrudRepository<Integer, Book> {

    public BookRepository(EntityManager em) {
        super(Book.class, em);
    }

}
