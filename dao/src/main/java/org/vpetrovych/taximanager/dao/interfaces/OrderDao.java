package org.vpetrovych.taximanager.dao.interfaces;

import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.Customer;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.domain.entities.User;

import java.util.List;

public interface OrderDao extends GenericDao<OrderEntity> {

    List<OrderEntity> findByDriver(User driver);

    List<OrderEntity> findByManager(User manager);

    List<OrderEntity> findByCustomer(Customer customer);

    List<OrderEntity> findByCriteria(OrderCriteria criteria);

}
