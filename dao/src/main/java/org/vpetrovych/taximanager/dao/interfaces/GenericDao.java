package org.vpetrovych.taximanager.dao.interfaces;

import java.util.Collection;
import java.util.List;

public interface GenericDao<T> {

    T findById(Long id);

    List<T> findAll();

    void save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteAll(Collection<T> entities);

}
