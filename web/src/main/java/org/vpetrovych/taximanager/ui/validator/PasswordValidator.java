package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

public class PasswordValidator implements Validator {

    private ValidatorException newValidatorException(String message){
        return new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    @Override
    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null || value.toString().isEmpty()) {
            throw newValidatorException("This field is required.");
        }

        String password = value.toString();

        if (password.length() < 8){
            throw newValidatorException("Min length is 8 characters");
        }
        if (password.length() > 12){
            throw newValidatorException("Max length is 12 characters");
        }

        if (!Pattern.matches(".*[A-Z]+.*", password)){
            throw newValidatorException("Requires at least 1 upper-case letter");
        }
        if (!Pattern.matches(".*[a-z]+.*", password)){
            throw newValidatorException("Requires at least 1 lower-case letter");
        }
        if (!Pattern.matches(".*[0-9]+.*", password)){
            throw newValidatorException("Requires at least 1 number");
        }
    }
}
