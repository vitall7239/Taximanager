package org.vpetrovych.taximanager.dao.implementations;

import org.junit.Assert;
import org.vpetrovych.taximanager.dao.configuration.PersistenceJPAConfig;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.vpetrovych.taximanager.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class)
public class UserDaoImplITest {

    private final UserDao userDao;
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String FATHER_NAME = "Father Name";
    private static final String EMAIL = "info@domain.com";
    private static final String PASSWORD = "my_password";
    private static final String PHONE = "+38(011)111-11-11";
    private static final int AGE = 18;

    private User testUser;
    private Role testRole;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Autowired
    public UserDaoImplITest(UserDao userDao) {
        this.userDao = userDao;
    }

    @BeforeEach
    public void init() {
        testRole = new Role("Admin");
        testUser = new User(PHONE, PASSWORD, FIRST_NAME, LAST_NAME, FATHER_NAME, EMAIL, AGE, testRole);
    }

    @Test
    public void findUserById() {
        userDao.save(testUser);
        entityManager.flush();
        entityManager.clear();
        Long id = testUser.getId();
        assertNotNull(id);

        User saved = userDao.findById(id);

        assertUser(testUser, saved);
    }

    @Test
    public void findUserByPhone() {
        userDao.save(testUser);
        entityManager.flush();
        entityManager.clear();

        User saved = userDao.findByPhone(PHONE);

        assertUser(testUser, saved);
    }

    @Test
    public void findAllWithEmptyCriteria(){
        userDao.save(testUser);
        entityManager.flush();
        entityManager.clear();
        UserCriteria criteria = new UserCriteria();
        criteria.setSort("lastName DESC");

        List<User> saved = userDao.findAll(criteria);

        Assert.assertEquals("Wrong Count", 1, saved.size());
    }

    @Test
    public void findAllWithCriteria(){
        userDao.save(testUser);
        entityManager.flush();
        entityManager.clear();
        UserCriteria criteria = new UserCriteria();
        criteria.setSort("lastName DESC");
        criteria.setFirstName("First");

        List<User> saved = userDao.findAll(criteria);

        Assert.assertEquals("Wrong Count", 1, saved.size());
    }

    private void assertUser(User expected, User saved){
        assertNotNull(saved);
        assertEquals(expected.getPhone(), saved.getPhone(), "phone wasn't saved");
        assertEquals(expected.getPassword(), saved.getPassword(), "password wasn't saved");
        assertEquals(expected.getFirstName(), saved.getFirstName(), "first name wasn't saved");
        assertEquals(expected.getLastName(), saved.getLastName(), "last name wasn't saved");
        assertEquals(expected.getFatherName(), saved.getFatherName(), "father name wasn't saved");
        assertEquals(expected.getEmail(), saved.getEmail(), "email wasn't saved");
        assertEquals(expected.getAge(), saved.getAge(), "email wasn't saved");
        assertEquals(expected.getRole(), saved.getRole(), "role wasn't saved");
    }
}
