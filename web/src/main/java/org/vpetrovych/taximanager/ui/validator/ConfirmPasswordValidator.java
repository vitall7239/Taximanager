package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ConfirmPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput passwordComponent = (UIInput) component.getAttributes().get("passwordComponent");
        String password = (String) passwordComponent.getValue();
        if (password == null){
            password = (String) passwordComponent.getSubmittedValue();
        }

        String confirmPassword = (String) value;

        if (confirmPassword == null || !confirmPassword.equals(password)) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Passwords must match", null));
        }
    }
}