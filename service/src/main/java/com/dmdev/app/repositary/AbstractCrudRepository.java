package com.dmdev.app.repositary;

import com.dmdev.app.entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
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
    public void delete(K id) {
        var entity = getById(id);
        entity.ifPresent(em::remove);
    }
}
