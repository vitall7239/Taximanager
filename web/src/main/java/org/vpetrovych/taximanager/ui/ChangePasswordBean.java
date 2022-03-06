package org.vpetrovych.taximanager.ui;

import org.vpetrovych.taximanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Controller(value = "changePasswordBean")
@Scope("session")
public class ChangePasswordBean {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String changePassword(){

        String error = null;

        try{
            userService.changePassword(email, currentPassword, newPassword);
        } catch (UsernameNotFoundException e){
            return "register.xhtml?faces-redirect=true";
        } catch (BadCredentialsException e){
            error = "Wrong password";
        } catch (AuthenticationException e){
            error = "Authentication error: " + e.getMessage();
        } catch (Exception e){
            error = "Internal server error: " + (e.getMessage() == null ? e.toString(): e.getMessage());
        }

        if (error == null){
            return "index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage("changePasswordForm:changePasswordErrors", new FacesMessage(error));
            return "changePassword.xhtml?error";
        }
    }

}
