package clases.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;


@SessionScoped
public class UsuarioSesionFactory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166346770501325807L;
	
	@Produces @QualifierUsuarioSesion @Default
	private UsuarioSesion usuarioSesion= new UsuarioSesion();
	
	@Produces @QualifierUsuarioSesionTienda
	private UsuarioSesion usuarioSesionTienda= new UsuarioSesionTienda();
	

}
