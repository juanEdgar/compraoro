package clases.ui.administracion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import clases.vo.cliente.Calle;
import ejb.bussines.compra.CalleEJB;

@ManagedBean
@FacesConverter(value="calleConverter")
public class CalleConverter implements Converter {
	
	@EJB
	private CalleEJB calleEJB;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return calleEJB.find(Integer.parseInt(value));
			} catch (Exception e) {}
		}
	    return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		return String.valueOf(((Calle) obj).getId());
	}

}
