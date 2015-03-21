package clases.vo.tienda.caja;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(schema="public", name="TiendaCajaEfectivoSaldo")
public class TiendaCajaEfectivoSaldo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8313273906258382277L;

	@Id
	@ManyToOne
	@JoinColumns({
		
		@JoinColumn(name="fiIdTienda", referencedColumnName="fiidtienda"),
		@JoinColumn(name="fiIdCaja", referencedColumnName="fiIdCaja")
	})
	private TiendaCajaEfectivo cajaEfectivo;
	
	@Id
	@Column(name="fnDenominacion")
	private float denominacion;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	
	@Column(name="fiCantidad")
	private int cantidad;
	
	@Transient
	private int cantidadDotar;
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj==null){
			return false;
		}
		if(!( obj instanceof TiendaCajaEfectivoSaldo)){
			return false;
		}
		
		TiendaCajaEfectivoSaldo tc= (TiendaCajaEfectivoSaldo)obj;
		
		
		
		if(tc.cajaEfectivo==null){
			return false;
		}
		
		return  this.cajaEfectivo.equals(tc.cajaEfectivo)&&this.denominacion==tc.denominacion;
		
	}

	@Override
	public int hashCode() {
	
		return  this.cajaEfectivo.hashCode()+(int)(this.denominacion*100);
	}

	public TiendaCajaEfectivo getCajaEfectivo() {
		return cajaEfectivo;
	}

	public void setCajaEfectivo(TiendaCajaEfectivo cajaEfectivo) {
		this.cajaEfectivo = cajaEfectivo;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadDotar() {
		return cantidadDotar;
	}

	public void setCantidadDotar(int cantidadDotar) {
		this.cantidadDotar = cantidadDotar;
	}
	
	
	
	
	
	
}
