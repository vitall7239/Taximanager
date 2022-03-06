package org.vpetrovych.taximanager.ui.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;

public class CarYearValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
        Date cur = new Date();
        if ((int)value < 2000) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vehicle must be manufactured no earlier than 2000", null));
        }else if((int)value > cur.getYear()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vehicle's manufacture year can not be more than the current year", null));
        }
    }
}
