package com.dmdev.app.repositary;

import com.dmdev.app.entity.PassportData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PassportDataRepository extends AbstractCrudRepository<Integer, PassportData> {

    public PassportDataRepository(EntityManager em) {
        super(PassportData.class, em);
    }

}