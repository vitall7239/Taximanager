package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.vpetrovych.taximanager.dao.interfaces.OrderDao;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.Customer;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.domain.entities.User;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class OrderDaoImpl extends GenericDaoImpl<OrderEntity> implements OrderDao {

    public OrderDaoImpl() {
        super(OrderEntity.class);
    }

    @Override
    public List<OrderEntity> findByDriver(User driver) {
        Assert.notNull(driver, "driver could not be null");
        return getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " o where o.driver = :driver", getClazz())
                .setParameter("driver", driver)
                .getResultList();
    }

    @Override
    public List<OrderEntity> findByManager(User manager) {
        Assert.notNull(manager, "manager could not be null");
        return getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " o where o.manager = :manager", getClazz())
                .setParameter("manager", manager)
                .getResultList();
    }

    @Override
    public List<OrderEntity> findByCustomer(Customer customer) {
        Assert.notNull(customer, "customer could not be null");
        return getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " o where o.customer = :customer", getClazz())
                .setParameter("customer", customer)
                .getResultList();
    }

    @Override
    public List<OrderEntity> findByCriteria(OrderCriteria criteria) {
        Assert.notNull(criteria, "customer could not be null");
        StringBuilder queryText = new StringBuilder("from " + getClazz().getSimpleName() + " o ");
        StringBuilder where = new StringBuilder();
        String orderBy = "";
        if (criteria.getDriver() != null) {
            whereBuilder(where, " o.driver = :driver ");
        }
        if (criteria.getManager() != null) {
            whereBuilder(where, " o.manager = :manager ");
        }
        if (criteria.getStatuses() != null) {
            whereBuilder(where, " o.status in (:status) ");
        }
        if (criteria.getOrder() != null) {
            orderBy = " ORDER BY " + criteria.getOrder();
        }
        TypedQuery<OrderEntity> query = getEntityManager().createQuery(queryText.append(where).append(orderBy).toString(), getClazz());
        if (criteria.getDriver() != null) {
            query.setParameter("driver", criteria.getDriver());
        }
        if (criteria.getManager() != null) {
            query.setParameter("manager", criteria.getManager());
        }
        if (criteria.getStatuses() != null) {
            query.setParameter("status", criteria.getStatuses());
        }
        return query.getResultList();
    }

}
