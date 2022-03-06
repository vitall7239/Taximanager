package org.vpetrovych.taximanager.service.implementations;

import org.springframework.util.CollectionUtils;
import org.vpetrovych.taximanager.dao.exception.ErrorObject;
import org.vpetrovych.taximanager.dao.exception.ProcessException;
import org.vpetrovych.taximanager.dao.interfaces.RoleDao;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.converter.UserDetailsDtoToUserConverter;
import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.dto.UserDetailsDto;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    @Transactional
    public void changePassword(String phone, String oldPassword, String newPassword) throws AuthenticationException {
        Assert.notNull(phone, "phone must not be null");
        Assert.notNull(oldPassword, "Old password must not be null");
        Assert.notNull(newPassword, "New password must not be null");

        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                oldPassword, userDetails.getAuthorities());

        //throws AuthenticationException
        authenticationManager.authenticate(authenticationToken);

        User user = userDao.findByPhone(phone);

        user.setPassword(newPassword);
        encodePassword(user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void register(UserDetailsDto userDetailsDto) throws AuthenticationException {
        Assert.notNull(userDetailsDto, "The userDetailsDto argument must not be null");
        Assert.hasText(userDetailsDto.getEmail(), "The user's email must not be empty or blank");
        Assert.hasText(userDetailsDto.getPassword(), "The user's password must not be empty or blank");
        Assert.hasText(userDetailsDto.getFirstName(), "The user's first name must not be empty or blank");
        Assert.hasText(userDetailsDto.getLastName(), "The user's last name must not be empty or blank");
        Assert.hasText(userDetailsDto.getFatherName(), "The user's father name must not be empty or blank");
        Assert.hasText(userDetailsDto.getPhone(), "The user's phone must not be empty or blank");

        List<User> savedUser = userDao.findByPhoneAndEmail(userDetailsDto.getPhone(), userDetailsDto.getEmail());
        if (!CollectionUtils.isEmpty(savedUser)) {
            List<ErrorObject> errorObjectList = new ArrayList<>();
            if(savedUser.stream().anyMatch(user -> userDetailsDto.getPhone().equals(user.getPhone()))) {
                errorObjectList.add(new ErrorObject("registerForm:phoneInput", "User with same phone is already registered"));
            }
            if(savedUser.stream().anyMatch(user -> userDetailsDto.getEmail().equals(user.getEmail()))) {
                errorObjectList.add(new ErrorObject("registerForm:phoneInput", "User with same email is already registered"));
            }
            throw new ProcessException(errorObjectList);
        }

        User user = new UserDetailsDtoToUserConverter().convert(userDetailsDto);
        user.setRole( roleDao.findByName("DRIVER") );
        encodePassword(user);
        userDao.save(user);
    }

    @Override
    public UserDetails login(String phone, String password) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        return userDetails;
    }

    @Override
    @Transactional
    public User findById(Long userId) {
        Assert.notNull(userId, "user id should be specified");
        return userDao.findById(userId);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String password) {
        user.setPassword(password);
        encodePassword(user);
        userDao.update(user);
    }

    @Override
    @Transactional
    public User updateRole(User user, String role) {
        Role curRole = roleDao.findByName(role);
        user.setRole(curRole);
        return user;
    }

    @Override
    public List<User> findAll(UserCriteria userCriteria) {
        return userDao.findAll(userCriteria);
    }
}