package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.transaction.annotation.Transactional;
import org.vpetrovych.taximanager.dao.interfaces.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Transactional
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private final Class<T> clazz;
    private EntityManager entityManager;

    protected GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected Class<T> getClazz() {
        return clazz;
    }

    @Override
    public T findById(Long id) {
        return getEntityManager().find(getClazz(), id);
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery("from " + getClazz().getSimpleName(), getClazz()).getResultList();
    }

    @Override
    public void save(T entity) {
        getEntityManager().persist(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public T update(T entity) {
        return getEntityManager().merge(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            for (T entity : entities) {
                this.delete(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            }
        }
    }

    protected void whereBuilder(StringBuilder where, String add) {
        if (where.toString().equals("")) {
            where.append(" WHERE ").append(add);
        } else {
            where.append(" AND ").append(add);
        }
    }
}
