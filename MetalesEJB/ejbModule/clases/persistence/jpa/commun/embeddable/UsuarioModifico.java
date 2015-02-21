package clases.persistence.jpa.commun.embeddable;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import clases.login.UsuarioSesion;


@Embeddable
public class UsuarioModifico {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fdFechaModificacion")
	private Date fecha;
	@Column(name="fcusuariomodifico")
	private String usuario;
		
	
	public UsuarioModifico(){
		
	}
	
	public UsuarioModifico(String  usuarioModifico){
		this.fecha= new Date();
		this.usuario=usuarioModifico;
	}
	
	

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public Date getFecha() {
		return new Date();
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	
	
}
