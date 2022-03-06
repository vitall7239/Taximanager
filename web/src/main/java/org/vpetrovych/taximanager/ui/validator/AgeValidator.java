package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class AgeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
        if ((int)value < 18) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Age must be at least 18 years old", null));
        }else if((int)value >= 60) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Age must be no more than 60 years old", null));
        }
    }

}
