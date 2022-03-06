package org.vpetrovych.taximanager.service.implementations;

import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByPhone(username);
        if(user == null){throw new UsernameNotFoundException(username);}
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
                Collections.singletonList( new SimpleGrantedAuthority(user.getRole().getName() )));
    }
}
