package com.dmdev.app.dao;

import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Client_;
import com.dmdev.app.entity.Initials_;
import com.dmdev.app.filters.ClientFilter;
import com.dmdev.app.predicates.CriteriaPredicate;
import com.dmdev.app.util.PredicateCreator;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;

import javax.persistence.criteria.Predicate;
import java.util.List;

import static com.dmdev.app.entity.QClient.client;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientDao {

    private static final ClientDao INSTANCE = new ClientDao();

    public static ClientDao getInstance(){
        return INSTANCE;
    }

    public List<Client> getClientsByQueryDsl(Session session, ClientFilter filter, RootGraph<Client> graph) {
        var clientPredicate = PredicateCreator.getClientPredicate(filter);
        return new JPAQuery<Client>(session)
                .select(client)
                .from(client)
                .where(clientPredicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                .fetch();
    }

    public List<Client> getClientsByCriteria(Session session, ClientFilter filter, RootGraph<Client> graph) {
        var cb = session.getCriteriaBuilder();
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
        return session.createQuery(criteria)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), graph)
                .list();

    }

}
