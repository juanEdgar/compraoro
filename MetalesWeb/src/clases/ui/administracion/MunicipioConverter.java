package clases.ui.administracion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import clases.vo.cliente.Municipio;
import ejb.bussines.compra.MunicipioEJB;

@ManagedBean
@FacesConverter(value="municipioConverter")
public class MunicipioConverter implements Converter {
	
	@EJB
	private MunicipioEJB municipioEJB;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			return municipioEJB.find(Integer.parseInt(value));
		}
	    return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		return String.valueOf(((Municipio) obj).getId());
	}

}
