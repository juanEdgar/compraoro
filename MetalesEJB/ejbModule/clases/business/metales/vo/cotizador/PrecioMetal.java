package clases.business.metales.vo.cotizador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.dinero.Moneda;

@Entity
@Table(schema="public", name="V_preciometalgramofino")
@SequenceGenerator(name="seq_metalPrecio", schema="public",sequenceName="seq_preciometal", initialValue=1,allocationSize=1)
public class PrecioMetal {

	
	public PrecioMetal(){
		
	}
	
	public PrecioMetal(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	
	@Id @GeneratedValue(generator="seq_metalPrecio")
	@Column(name="fiidpreciometalgramofino")
	private int id;	
	
	@OneToOne
	@JoinColumn(name="fiidmoneda",nullable=false)
	private Moneda moneda;
	
	@Column(name="fiidestatus")
	private int estatus=1;
	
	@OneToOne
	@JoinColumn(name="fiidmetal", nullable=false)
	private Metal metal;
	
	@Column(name="fdprecio")
	private float precio;
	
	private UsuarioModifico usuarioModifico;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Metal getMetal() {
		return metal;
	}

	public void setMetal(Metal metal) {
		this.metal = metal;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
	
}
