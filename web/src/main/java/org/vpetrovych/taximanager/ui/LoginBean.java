package org.vpetrovych.taximanager.ui;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.faces.flow.FlowScoped;

@Component("loginBean")
@Scope(value="flow")
@FlowScoped(value="login")
public class LoginBean {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private String phone = "";
    private String password = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login(){
        UserDetails userDetails;
        try{
            userDetails = userService.login(phone, password);
        } catch (UsernameNotFoundException e){
            BeanUtils.addMessageToFacesContext( "loginForm:phoneInput", "User not found" );
            return "error";
        } catch (BadCredentialsException e){
            BeanUtils.addMessageToFacesContext( "loginForm:loginPass", "Wrong password" );
            return "error";
        } catch (AuthenticationException e){
            BeanUtils.addMessageToFacesContext( "loginForm:loginPass", "Wrong password" );
            return "error";
        } catch (Exception e){
            BeanUtils.addMessageToFacesContext( "loginForm:loginPass", "Server error" );
            return "error";
        }
        switch(userDetails.getAuthorities().iterator().next().getAuthority()){
            case "ADMIN" : return "admin";
            case "MANAGER" : return "manager";
            default : return "driver";
        }
    }
}
