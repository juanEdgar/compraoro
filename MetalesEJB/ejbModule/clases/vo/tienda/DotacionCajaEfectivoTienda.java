package clases.vo.tienda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;

@Entity
@Table(schema="public", name="DotacionCajaEfectivoTienda")
@SequenceGenerator(schema="public", name="seq_dotacionCajaTienda", sequenceName="seq_dotacionCajaTienda", allocationSize=10)
public class DotacionCajaEfectivoTienda {
	
	@Id
	@GeneratedValue(generator="seq_dotacionCajaTienda")
	private int id;
	@ManyToOne
	@JoinColumn(name="fiIdCajaEfectivoTienda")
	private CajaEfectivoTienda cajaTienda;	
	@Column(name="ficantidadinicial")	
	private int cantidadInicial;
	@Column(name="ficantidadActual")
	private int cantidadActual;
	@Column(name="fnDenominacion")
	private float denominacion;
	@Column(name="fiIdEstatus")
	private int estatus;
	
	private UsuarioModifico usuarioModifico;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CajaEfectivoTienda getCajaTienda() {
		return cajaTienda;
	}

	public void setCajaTienda(CajaEfectivoTienda cajaTienda) {
		this.cajaTienda = cajaTienda;
	}

	public int getCantidadInicial() {
		return cantidadInicial;
	}

	public void setCantidadInicial(int cantidadInicial) {
		this.cantidadInicial = cantidadInicial;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	public float getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(float denominacion) {
		this.denominacion = denominacion;
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
		if(!( obj instanceof DotacionCajaEfectivoTienda)){
			return false;
		}
		
		return ((DotacionCajaEfectivoTienda)obj).id==this.id;
	}
	
	
	@Override
	public int hashCode() {
			return this.id;
	}
	
}
