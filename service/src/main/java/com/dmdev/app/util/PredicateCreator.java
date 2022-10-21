package com.dmdev.app.util;

import com.dmdev.app.filters.ClientFilter;
import com.dmdev.app.predicates.QPredicate;
import com.querydsl.core.types.Predicate;

import static com.dmdev.app.entity.QClient.client;

public class PredicateCreator {
    public static Predicate getClientPredicate(ClientFilter filter) {
        return QPredicate.build()
                .addPredicate(filter.getFirstName(), client.initials.firstName::eq)
                .addPredicate(filter.getSecondName(), client.initials.secondName::eq)
                .addPredicate(filter.getMiddleName(), client.initials.middleName::eq)
                .buildConjunction();
    }

}
