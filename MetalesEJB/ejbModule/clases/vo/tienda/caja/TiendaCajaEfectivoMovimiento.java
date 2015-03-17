package clases.vo.tienda.caja;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;

@Entity
@Table(schema="public", name="TiendaCajaEfectivoMovimiento")
@SequenceGenerator(schema="public", name="seq_tiendacajaefectivomovimiento", sequenceName="seq_tiendacajaefectivomovimiento", allocationSize=5)
public class TiendaCajaEfectivoMovimiento {
	
	public TiendaCajaEfectivoMovimiento(){
		
	}
	
	@Id
	@GeneratedValue(generator="seq_tiendacajaefectivomovimiento")
	@Column(name="fiIdCajaEfectivoMovimiento")
	private int id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="fiIdCaja", referencedColumnName="fiIdCaja"),
		@JoinColumn(name="fiIdTienda", referencedColumnName="fiIdTienda")
	})
	private TiendaCajaEfectivo tiendaCaja;
	
	@Column(name="fnDenominacion")
	private float denominacion;
	
	@Column(name="ficantidad")
	private int cantidad;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	private UsuarioModifico usuarioModifico;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TiendaCajaEfectivo getTiendaCaja() {
		return tiendaCaja;
	}

	public void setTiendaCaja(TiendaCajaEfectivo tiendaCaja) {
		this.tiendaCaja = tiendaCaja;
	}

	public float getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(float denominacion) {
		this.denominacion = denominacion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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
	
	
	
	
}
