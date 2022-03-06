package org.vpetrovych.taximanager.dao.implementations;

import org.vpetrovych.taximanager.dao.configuration.PersistenceJPAConfig;
import org.vpetrovych.taximanager.dao.interfaces.RoleDao;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.junit.jupiter.api.Assertions;
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

@Rollback
@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceJPAConfig.class)
public class RoleDaoImplITest {

    private final RoleDao roleDao;

    private EntityManager manager;

    @Autowired
    public RoleDaoImplITest(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @PersistenceContext
    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    @Test
    public void findById() {
        Role role = new Role( "Test_FindById");

        roleDao.save(role);

        manager.flush();
        manager.clear();

        Long id = role.getId();
        Assertions.assertNotNull(id);

        Role saved = roleDao.findById(id);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(role, saved);
    }

    @Test
    public void update() {
        Role role = new Role("Test:Update");
        roleDao.save(role);

        manager.flush();
        manager.clear();

        Long id = role.getId();
        Assertions.assertNotNull(id);

        role.setName("Test:Updated");

        Role updated = roleDao.update(role);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Test:Updated", updated.getName());
        Assertions.assertEquals(updated, role);
    }

    @Test
    public void findAll() {
        Role firstRole = new Role("First Role");
        Role secondRole = new Role("Second Role");

        roleDao.save(firstRole);
        roleDao.save(secondRole);

        manager.flush();
        manager.clear();

        List<Role> allReceivedRoles = roleDao.findAll();
        Assertions.assertTrue(allReceivedRoles.contains(firstRole));
        Assertions.assertTrue(allReceivedRoles.contains(secondRole));
    }

}