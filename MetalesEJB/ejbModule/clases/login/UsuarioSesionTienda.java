package clases.login;

import java.security.InvalidParameterException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import clases.vo.tienda.Tienda;


public class UsuarioSesionTienda extends UsuarioSesion {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7702421864896796699L;

	@Inject
	public UsuarioSesionTienda(){
		System.out.println("Iniciando usuario sesion teinda");
	}
	
	{
		Tienda t= new Tienda();
		t.setId(1);
		this.tienda=t;
		
	}
	
	public UsuarioSesionTienda(Tienda tienda){
		if(tienda==null){
			throw new InvalidParameterException("La tienda a asociar al empleado sesion es nula");
		}
		if(tienda.getId()<0){
			throw new InvalidParameterException("La tienda a asociar al empleado sesion es invalida ID: "+tienda.getId());
		}
		
		this.tienda=tienda;
		
		
	}
	
	
	private Tienda tienda;


	public Tienda getTienda() {
		return tienda;
	}


	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}
	
	
	
	
	
}
