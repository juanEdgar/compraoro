package clases.business.metales.vo.compra;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;


@Entity
@Table(schema="public", name="bitacoracompra")
@SequenceGenerator(name="seq_bitacoraCompra", schema="public", sequenceName="seq_bitacoraCompra", allocationSize=50)
public class BitacoraCompra {
		
	
	public BitacoraCompra() {
		
	}
	
	public BitacoraCompra(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	public BitacoraCompra(Compra compraMetal) {
		
		this.idCompra= compraMetal.getId();
		this.estatus= compraMetal.getEstatus().getId();
		
	}

	@Column(name="fiIdBitacoraCompra")
	@Id @GeneratedValue(generator="seq_bitacoraCompra")
	private int id;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	@Column(name="fiidcompra")
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

	public UsuarioModifico getUsuarioModifico() {
		return usuarioModifico;
	}
	
	
	
	
	
}
