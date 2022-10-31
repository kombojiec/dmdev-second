package com.dmdev.app.repositary;

import com.dmdev.app.entity.Book;

import javax.persistence.EntityManager;
import java.util.List;

public class BookRepository extends AbstractCrudRepository<Integer, Book> {

    public BookRepository(EntityManager em) {
        super(Book.class, em);
    }

    @Override
    public List<Book> get() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

}
