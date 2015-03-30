package clases.business.metales.vo.cotizador;

import java.io.Serializable;

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
@Table(schema="public", name="V_PrecioDiamanteQuilate")
@SequenceGenerator(schema="public", sequenceName="SEQ_PRECIODIAMANTE", name="SEQ_PRECIODIAMANTE", allocationSize=50)
public class PrecioDiamante implements Serializable {


	private static final long serialVersionUID = 6969023689550548034L;

	public PrecioDiamante() {
	}
	
	public PrecioDiamante(int id) {
		this.id=id;
	}
	
	@Id @GeneratedValue(generator="SEQ_PRECIODIAMANTE")
	@Column(name="fiidpreciodiamante")
	private int id;
	
	@OneToOne
	@JoinColumn(name="fiIdTipoDiamante")
	private Diamante tipoDiamante;
	
	@OneToOne
	@JoinColumn(name="fiidmoneda")
	private Moneda moneda;
	
	@Column(name="fnPrecio")
	private float precio;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	private UsuarioModifico usuarioModifico;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Diamante getTipoDiamante() {
		return tipoDiamante;
	}

	public void setTipoDiamante(Diamante tipoDiamante) {
		this.tipoDiamante = tipoDiamante;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public UsuarioModifico getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof PrecioDiamante)){
			return false;
		}
		
		return ((PrecioDiamante)obj).getId()==this.getId();
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
}
