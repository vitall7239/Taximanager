package org.vpetrovych.taximanager.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;

import java.util.List;

public interface OrderService {

    List<OrderEntity> findOrderByCriteria(OrderCriteria criteria);

    void saveOrder(OrderEntity order, UserDetails userDetails);

    void setDriver(OrderEntity order, UserDetails userDetails);

    void update(OrderEntity order);

    void delete(OrderEntity order);

}
