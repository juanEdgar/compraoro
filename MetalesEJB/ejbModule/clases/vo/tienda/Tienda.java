package clases.vo.tienda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.catalogo.Estatus;

@Entity
@Table(schema="public" ,name="Tienda")
@SequenceGenerator(name="seq_tienda", allocationSize=1, schema="public", sequenceName="seq_tienda")
public class Tienda {

	public Tienda(){
		
	}
	
	public Tienda(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	@Id @GeneratedValue(generator="seq_tienda")
	@Column(name="fiidtienda")
	private int id;
	
	@Column(name="fiNumero")
	private int numero;
	
	@Column(name="fcNombre")
	private String nombre;
	
	@OneToOne
	@JoinColumn(name="fiIdEstatus")
	private Estatus estatus;
	

	
	private UsuarioModifico usuarioModifico;
				
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public UsuarioModifico getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	
	
	
	
}
