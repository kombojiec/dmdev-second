package com.dmdev.app.repositary;

import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Client_;
import com.dmdev.app.entity.Initials_;
import com.dmdev.app.filters.ClientFilter;
import com.dmdev.app.predicates.CriteriaPredicate;
import com.dmdev.app.util.PredicateCreator;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;

import static com.dmdev.app.entity.QClient.client;

@Repository
public class ClientRepository extends AbstractCrudRepository<Integer, Client> {

    public ClientRepository(EntityManager em) {
        super(Client.class, em);
    }

    public List<Client> getClientsByQueryDsl(ClientFilter filter, EntityGraph<Client> graph) {
        var clientPredicate = PredicateCreator.getClientPredicate(filter);
        return new JPAQuery<Client>(em)
                .select(client)
                .from(client)
                .where(clientPredicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .fetch();
    }

    public List<Client> getClientsByCriteria(ClientFilter filter, EntityGraph<Client> graph) {
        var cb = em.getCriteriaBuilder();
        var criteria = cb.createQuery(Client.class);
        var client = criteria.from(Client.class);
        var predicates = CriteriaPredicate.build()
                .addPredicate(filter.getFirstName(),
                        firstName -> cb.equal(client.get(Client_.initials).get(Initials_.firstName), firstName))
                .addPredicate(filter.getSecondName(),
                        secondName -> cb.equal(client.get(Client_.initials).get(Initials_.secondName), secondName))
                .addPredicate(filter.getMiddleName(),
                        middleName -> cb.equal(client.get(Client_.initials).get(Initials_.middleName), middleName));

        criteria.where(predicates.getPredicates().toArray(Predicate[]::new));
        return em.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .getResultList();
    }

}
