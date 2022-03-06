package org.vpetrovych.taximanager.dao.implementations;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vpetrovych.taximanager.dao.configuration.PersistenceJPAConfig;
import org.vpetrovych.taximanager.dao.interfaces.CustomerDao;
import org.vpetrovych.taximanager.dao.interfaces.OrderDao;
import org.vpetrovych.taximanager.dao.interfaces.RoleDao;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.Customer;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.domain.entities.OrderStatus;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.vpetrovych.taximanager.domain.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class)
class OrderDaoImplTest {

    private OrderDao orderDao;

    private UserDao userDao;

    private RoleDao roleDao;

    private CustomerDao customerDao;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public OrderDaoImplTest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Test
    void save() {
        Role driverRole = new Role("TestDriver");
        roleDao.save(driverRole);
        User driver = new User();
        driver.setRole(driverRole);
        driver.setAge(20);
        driver.setEmail("testEmail");
        driver.setFirstName("Driver");
        driver.setLastName("Driver");
        driver.setFatherName("Driver");
        driver.setPassword("Driver");
        driver.setPhone("Driver");
        userDao.save(driver);
        Role managerRole = new Role("TestManager");
        roleDao.save(managerRole);
        User manager = new User();
        manager.setRole(managerRole);
        manager.setAge(20);
        manager.setEmail("testManagerEmail");
        manager.setFirstName("Manager");
        manager.setLastName("Manager");
        manager.setFatherName("Manager");
        manager.setPassword("Manager");
        manager.setPhone("Manager");
        userDao.save(manager);
        Customer customer = new Customer();
        customer.setName("Customer");
        customer.setPhone("Customer");
        customerDao.save(customer);
        OrderEntity order = new OrderEntity();
        order.setManager(manager);
        order.setCustomer(customer);
        order.setDriver(driver);
        order.setOrderTime(new Date());
        order.setAddressFrom("From");
        order.setAddressTo("To");
        order.setDescription("desc");
        order.setPrice(BigDecimal.valueOf(50.50));
        order.setStatus(OrderStatus.ACTIVE.toString());

        orderDao.save(order);
        Long id = order.getId();

        entityManager.clear();
        entityManager.flush();

        OrderEntity saved = orderDao.findById(id);

        Assert.assertNotNull(saved);
    }

    @Test
    void findByCriteria() {
        Role driverRole = new Role("TestDriver");
        User driver = new User();
        driver.setRole(driverRole);
        driver.setAge(20);
        driver.setEmail("testEmail");
        driver.setFirstName("Driver");
        driver.setLastName("Driver");
        driver.setFatherName("Driver");
        driver.setPassword("Driver");
        driver.setPhone("Driver");
        Role managerRole = new Role("TestManager");
        User manager = new User();
        manager.setRole(managerRole);
        manager.setAge(20);
        manager.setEmail("testManagerEmail");
        manager.setFirstName("Manager");
        manager.setLastName("Manager");
        manager.setFatherName("Manager");
        manager.setPassword("Manager");
        manager.setPhone("Manager");
        Customer customer = new Customer();
        customer.setName("Customer");
        customer.setPhone("Customer");
        OrderEntity order = new OrderEntity();
        order.setManager(manager);
        order.setCustomer(customer);
        order.setDriver(driver);
        order.setOrderTime(new Date());
        order.setAddressFrom("From");
        order.setAddressTo("To");
        order.setDescription("desc");
        order.setPrice(BigDecimal.valueOf(50.50));
        order.setStatus(OrderStatus.ACTIVE.toString());

        orderDao.save(order);
        Long id = order.getId();

        entityManager.clear();
        entityManager.flush();

        OrderCriteria criteria = new OrderCriteria();
        criteria.setOrder(" orderTime ASC");

        List<OrderEntity> saved = orderDao.findByCriteria(criteria);

        Assert.assertEquals("Wrong size",1 , saved.size());
    }
}