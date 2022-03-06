package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

public class UserNameValidator implements Validator {
    private static final String NAME_REGEXP = "[A-Za-zА-Яа-я]+";
    private static Pattern pattern = Pattern.compile(NAME_REGEXP);

    @Override
    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null || value.toString().isEmpty()) {
            return;
        }
        if (!pattern.matcher(value.toString().trim()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Only letters allow", null));
        }
    }
}
