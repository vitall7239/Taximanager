package org.vpetrovych.taximanager.dao.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.vpetrovych.taximanager.dao.configuration.PersistenceJPAConfig;
import org.vpetrovych.taximanager.dao.interfaces.CarDao;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.vpetrovych.taximanager.domain.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class)
public class CarDaoImplTest {

    private Car testCar;
    private User testUser;
    private Role testRole;
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String FATHER_NAME = "Father Name";
    private static final String EMAIL = "info@domain.com";
    private static final String PASSWORD = "my_password";
    private static final String PHONE = "+38(011)111-11-11";
    private static final int AGE = 18;
    private static final String MARK = "testMark";
    private static final String MODEL = "testModel";
    private static final String COLOR = "testColor";
    private static final String NUMBER = "testNumber";
    private static final String DRIVER_NUMBER = "testDriverNumber";
    private static final int YEAR = 2000;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private final CarDao testObj;
    private final UserDao userDao;

    @Autowired
    CarDaoImplTest(CarDao carDao, UserDao userDao){
        this.testObj = carDao;
        this.userDao = userDao;
    }

    @BeforeEach
    public void setup(){
        testRole = new Role("Admin");
        testUser = new User(PHONE, PASSWORD, FIRST_NAME, LAST_NAME, FATHER_NAME, EMAIL, AGE, testRole);
        userDao.save(testUser);
    }

    @Test
    public void findById(){
        testCar = new Car();
        testCar.setMark(MARK);
        testCar.setModel(MODEL);
        testCar.setColor(COLOR);
        testCar.setYear(YEAR);
        testCar.setNumber(NUMBER);
        testCar.setDriverNumber(DRIVER_NUMBER);
        testCar.setUser(testUser);
        testObj.save(testCar);
        long ind = testCar.getId();
        entityManager.flush();
        entityManager.clear();

        Car saved = testObj.findById(ind);

        assertCar(testCar, saved);
    }

    @Test
    public void findByUser(){
        testCar = new Car();
        testCar.setMark(MARK);
        testCar.setModel(MODEL);
        testCar.setColor(COLOR);
        testCar.setYear(YEAR);
        testCar.setNumber(NUMBER);
        testCar.setDriverNumber(DRIVER_NUMBER);
        testCar.setUser(testUser);
        testObj.save(testCar);
        entityManager.flush();
        entityManager.clear();

        Car saved = testObj.findByUser(testUser);

        assertCar(testCar, saved);
    }

    private void assertCar(Car expected, Car saved){
        assertNotNull(saved);
        assertEquals(expected.getMark(), saved.getMark(), "mark wasn't saved");
        assertEquals(expected.getModel(), saved.getModel(), "model wasn't saved");
        assertEquals(expected.getColor(), saved.getColor(), "color wasn't saved");
        assertEquals(expected.getYear(), saved.getYear(), "year wasn't saved");
        assertEquals(expected.getNumber(), saved.getNumber(), "number wasn't saved");
        assertEquals(expected.getDriverNumber(), saved.getDriverNumber(), "driver number wasn't saved");
        assertEquals(expected.getUser(), saved.getUser(), "user wasn't saved");
    }

}
