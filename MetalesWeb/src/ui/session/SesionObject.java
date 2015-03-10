package ui.session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import clases.login.UsuarioSesion;

@ManagedBean
@SessionScoped
public class SesionObject {

	@Inject
	private UsuarioSesion usuario;
	
	
	public String getNombreUsuario(){
		return usuario.getNombreUsuario();
	}
	
	public String getUbicacion(){
		return usuario.getTienda().getNombre();
	}
}
