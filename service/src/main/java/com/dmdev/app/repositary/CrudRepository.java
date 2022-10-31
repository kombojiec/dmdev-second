package com.dmdev.app.repositary;

import com.dmdev.app.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CrudRepository<K extends Serializable, V extends BaseEntity<K>> {

    V save(V entity);

    default Optional<V> getById(K id) {
        return getById(id, Collections.emptyMap());
    }

    Optional<V> getById(K id, Map<String, Object> props);

    V update(V entity);

    void delete(K id);

    List<V> get();

}
