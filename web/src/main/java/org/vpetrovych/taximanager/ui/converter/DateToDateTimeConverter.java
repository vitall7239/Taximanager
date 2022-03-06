package org.vpetrovych.taximanager.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@FacesConverter(forClass = Date.class, value = "dateToDateTimeConverter")
public class DateToDateTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return new Date();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Date date = (Date) o;
        DateFormat dateFormat = new SimpleDateFormat("d/M/yy - HH:mm");
        return dateFormat.format(date);
    }

}
