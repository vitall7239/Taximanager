package org.vpetrovych.taximanager.dao.interfaces;

import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.entities.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    User findByPhone(String phone);

    List<User> findByPhoneAndEmail(String phone, String email);

    List<User> findAll(UserCriteria criteria);

}
