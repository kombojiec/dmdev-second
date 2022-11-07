package com.dmdev.app.repositary;

import com.dmdev.app.entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCrudRepository<K extends Serializable, V extends BaseEntity<K>> implements CrudRepository<K, V> {

    private final Class<V> clazz;
    protected final EntityManager em;

    @Override
    public V save(V entity) {
        return em.merge(entity);
    }

    @Override
    public Optional<V> getById(K id, Map<String, Object> props) {
        return Optional.ofNullable(em.find(clazz, id, props));
    }

    @Override
    public V update(V entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(V entity) {
        var result = getById(entity.getId());
        result.ifPresent(em::remove);
        em.flush();
    }

    @Override
    public List<V> getAll() {
        System.out.println(clazz);
        return em.createQuery(String.format("select entity from %s entity", clazz.getName())).getResultList();
    }
}
