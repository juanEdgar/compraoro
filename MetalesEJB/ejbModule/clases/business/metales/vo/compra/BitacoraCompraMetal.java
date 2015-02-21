package clases.business.metales.vo.compra;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;


@Entity
@Table(schema="public", name="bitacoracomprametal")
@SequenceGenerator(name="seq_bitacoraCompraMetal", schema="public", sequenceName="seq_bitacoraCompraMetal", allocationSize=50)
public class BitacoraCompraMetal {
		
	
	public BitacoraCompraMetal() {
		
	}
	
	public BitacoraCompraMetal(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	public BitacoraCompraMetal(CompraMetal compraMetal) {
		
		this.idCompra= compraMetal.getId();
		this.estatus= compraMetal.getEstatus().getId();
		
	}

	@Column(name="fiIdBitacoraCompraMetal")
	@Id @GeneratedValue(generator="seq_bitacoraCompraMetal")
	private int id;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	@Column(name="fiidcomprametal")
	private int idCompra;
	
	private UsuarioModifico usuarioModifico;

	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	
	
	
	
	
}
