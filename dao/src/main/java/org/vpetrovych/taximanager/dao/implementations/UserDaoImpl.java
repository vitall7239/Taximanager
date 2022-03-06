package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByPhone(String phone) {
        Assert.notNull(phone, "phone could not be null");
        List<User> userList = getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " u where u.phone = :phone", getClazz())
                .setParameter("phone", phone)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        } else if (userList.size() == 1) {
            return userList.get(0);
        }
        throw new NonUniqueResultException();
    }

    @Override
    public List<User> findByPhoneAndEmail(String phone, String email) {
        Assert.notNull(phone, "phone could not be null");
        Assert.notNull(email, "email could not be null");
        return getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " u where u.phone = :phone or u.email = :email ", getClazz())
                .setParameter("phone", phone)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<User> findAll(UserCriteria criteria) {
        Assert.notNull(criteria, "criteria could not be null");
        String queryText = "SELECT u FROM User u "
                + " LEFT JOIN Role r ON r = u.role "
                + " LEFT JOIN Car c ON c.user = u ";
        String whereText = buildWhereFromCriteria(criteria);
        String sortText = " ORDER BY u." + criteria.getSort();
        return getEntityManager()
                .createQuery(queryText + whereText + sortText, User.class)
                .getResultList();
    }

    private String buildWhereFromCriteria(UserCriteria criteria) {
        StringBuilder whereText = new StringBuilder();
        if (criteria.getFirstName() != null) {
            whereBuilder(whereText, " u.firstName LIKE '%" + criteria.getFirstName() + "%' ");
        }
        if (criteria.getLastName() != null) {
            whereBuilder(whereText, " u.lastName LIKE '%" + criteria.getLastName() + "%' ");
        }
        if (criteria.getFatherName() != null) {
            whereBuilder(whereText, " u.fatherName LIKE '%" + criteria.getFatherName() + "%' ");
        }
        if (criteria.getAge() != null) {
            whereBuilder(whereText, " u.age LIKE '%" + criteria.getAge() + "%' ");
        }
        if (criteria.getEmail() != null) {
            whereBuilder(whereText, " u.email LIKE '%" + criteria.getEmail() + "%' ");
        }
        if (criteria.getPhone() != null) {
            whereBuilder(whereText, " u.phone LIKE '%" + criteria.getPhone() + "%' ");
        }
        if (criteria.getMark() != null) {
            whereBuilder(whereText, " c.mark LIKE '%" + criteria.getMark() + "%' ");
        }
        if (criteria.getModel() != null) {
            whereBuilder(whereText, " c.model LIKE '%" + criteria.getModel() + "%' ");
        }
        if (criteria.getColor() != null) {
            whereBuilder(whereText, " c.color LIKE '%" + criteria.getColor() + "%' ");
        }
        if (criteria.getYear() != null) {
            whereBuilder(whereText, " c.year LIKE '%" + criteria.getYear() + "%' ");
        }
        if (criteria.getNumber() != null) {
            whereBuilder(whereText, " c.number LIKE '%" + criteria.getNumber() + "%' ");
        }
        if (criteria.getDriverNumber() != null) {
            whereBuilder(whereText, " c.driverNumber LIKE '%" + criteria.getDriverNumber() + "%' ");
        }
        if (criteria.getRole() != null) {
            whereBuilder(whereText, " r.name LIKE '" + criteria.getRole() + "' ");
        }
        return whereText.toString();
    }

}
