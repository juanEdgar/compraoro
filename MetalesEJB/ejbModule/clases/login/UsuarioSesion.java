package clases.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.vo.tienda.Tienda;
import clases.vo.tienda.caja.TiendaCajaEfectivo;

@SessionScoped
public class UsuarioSesion implements Serializable{
	
	
	protected static final Logger log = LogManager.getLogger(UsuarioSesion.class);
	
	private static final long serialVersionUID = 8469525259835169321L;
	private String nombreUsuario="SYS";
	private Tienda tienda;
	private TiendaCajaEfectivo caja;
	
	@Inject
	public UsuarioSesion(){
		System.out.println("Iniciando usuario sesion normal");
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	@PostConstruct
	private void init(){
		log.debug("Iniciando la sesion de usuario");
		this.nombreUsuario="SYS";
		this.tienda= new Tienda();
	}


	public Tienda getTienda() {
		return tienda;
	}


	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}


	public TiendaCajaEfectivo getCaja() {
		return caja;
	}


	public void setCaja(TiendaCajaEfectivo caja) {
		this.caja = caja;
	}
	
	
	
}
