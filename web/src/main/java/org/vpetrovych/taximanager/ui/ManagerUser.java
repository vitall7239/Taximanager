package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;

@Component("managerUser")
@Scope(value="flow")
@FlowScoped(value="manager")
public class ManagerUser {

    private User user;

    private UserService userService;

    public ManagerUser(){}

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void init(){
        user = userService.findByPhone(BeanUtils.getCurrentUser().getUsername());
    }

    public void saveUser(){
        userService.updateUser(user);
    }
}
