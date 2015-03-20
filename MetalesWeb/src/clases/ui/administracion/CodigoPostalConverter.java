package clases.ui.administracion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import clases.vo.cliente.CodigoPostal;
import ejb.bussines.compra.CodigoPostalEJB;

@ManagedBean
@FacesConverter(value="codigoPostalConverter")
public class CodigoPostalConverter implements Converter {
	
	@EJB
	private CodigoPostalEJB cpEJB;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			return cpEJB.find(Integer.parseInt(value));
		}
	    return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		return String.valueOf(((CodigoPostal) obj).getId());
	}

}
