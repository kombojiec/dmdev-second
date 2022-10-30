package com.dmdev.app.predicates;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaPredicate {
    private final List<Predicate> predicates = new ArrayList<>();

    public static CriteriaPredicate build() {
        return new CriteriaPredicate();
    }

    public <T> CriteriaPredicate addPredicate(T object, Function<T, Predicate> function) {
        if(object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }
}
