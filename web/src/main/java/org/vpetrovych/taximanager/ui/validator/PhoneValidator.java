package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

public class PhoneValidator implements Validator {
    private static final String EMAIL_REGEXP = "^[+]{1}[3]{1}[8]{1}[(][0][0-9][0-9][)]{0,1}[0-9]{3}[-]{1}[0-9]{2}[-]{1}[0-9]{2}$";
    private static Pattern pattern = Pattern.compile(EMAIL_REGEXP);

    @Override
    public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
        if (value == null || value.toString().isEmpty()) {
            return;
        }
        if (!pattern.matcher(value.toString().trim()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong phone format", null));
        }
    }
}

