package org.vpetrovych.taximanager.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.dto.UserDetailsDto;
import org.vpetrovych.taximanager.domain.entities.User;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public interface UserService {

    void save(User user);

    void changePassword(String phone, String oldPassword, String newPassword) throws AuthenticationException;

    void register(UserDetailsDto userDetailsDto) throws AuthenticationException;

    UserDetails login(String email, String password);

    void updateUser(User user);

    void updatePassword(User user, String password);

    User updateRole(User user, String role);

    List<User> findAll(UserCriteria userCriteria);

    User findById(Long id);

    User findByPhone(String phone);
}
