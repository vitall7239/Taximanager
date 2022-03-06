package org.vpetrovych.taximanager.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.dao.interfaces.CustomerDao;
import org.vpetrovych.taximanager.dao.interfaces.OrderDao;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.Customer;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.domain.entities.OrderStatus;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.OrderService;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private CustomerDao customerDao;
    private UserDao userDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<OrderEntity> findOrderByCriteria(OrderCriteria criteria) {
        return orderDao.findByCriteria(criteria);
    }

    @Override
    public void saveOrder(OrderEntity order, UserDetails userDetails){
        Customer customer = customerDao.findByPhone(order.getCustomer().getPhone());
        if(customer != null){
            order.setCustomer(customer);
        }else{
            customerDao.save(order.getCustomer());
        }
        if(order.getId() == null){
            order.setOrderTime(new Date());
            order.setStatus(OrderStatus.ACTIVE.name());
            User manager = userDao.findByPhone(userDetails.getUsername());
            order.setManager(manager);
            orderDao.save(order);
        }else{
            orderDao.update(order);
        }

    }

    @Override
    public void setDriver(OrderEntity order, UserDetails userDetails){
        order.setStatus(OrderStatus.ACCEPTED.name());
        User driver = userDao.findByPhone(userDetails.getUsername());
        order.setDriver(driver);
        orderDao.update(order);
    }

    @Override
    public void update(OrderEntity order){
        orderDao.update(order);
    }

    @Override
    public void delete(OrderEntity order){
        orderDao.delete(order);
    }
}
