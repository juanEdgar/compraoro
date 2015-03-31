package clases.ui.administracion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import clases.vo.cliente.Estado;
import ejb.bussines.compra.EstadoEJB;

@ManagedBean
@FacesConverter(value="estadoConverter")
public class EstadoConverter implements Converter {
	
	@EJB
	private EstadoEJB estadoEJB;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return estadoEJB.find(Integer.parseInt(value));
			} catch (Exception e) {}
		}
	    return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		return String.valueOf(((Estado) obj).getId());
	}
	
}
