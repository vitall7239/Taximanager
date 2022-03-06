package org.vpetrovych.taximanager.ui;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.dao.exception.ErrorObject;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

public class BeanUtils {

    public static void addMessageToFacesContext(String objectName, String message){
        FacesContext.getCurrentInstance().addMessage(objectName, new FacesMessage(message));
    }

    public static void addMessageToFacesContext(ErrorObject error){
        addMessageToFacesContext(error.getObjectName(), error.getObjectMessage());
    }

    public static void addMessageToFacesContext(List<ErrorObject> errors){
        errors.forEach(
                error -> addMessageToFacesContext(error.getObjectName(), error.getObjectMessage())
        );
    }

    public static UserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }
        return userDetails;
    }

}
