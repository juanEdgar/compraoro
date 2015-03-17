package clases.vo.tienda.caja;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import clases.vo.tienda.Tienda;


@Entity
@Table(schema="public", name="TiendaCajaEfectivo")
public class TiendaCajaEfectivo implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5532591833370240144L;

	@Id
	@ManyToOne
	@JoinColumn(name="fiidtienda")
	private Tienda tienda;
	
	@Id	
	@Column(name="fiIdCaja")
	private int id;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	

	@OneToMany(mappedBy="cajaEfectivo")
	@Basic(fetch=FetchType.EAGER)
	@OrderBy("denominacion ASC")
	private List<TiendaCajaEfectivoSaldo> saldos;
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	
	

	

	public List<TiendaCajaEfectivoSaldo> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<TiendaCajaEfectivoSaldo> saldos) {
		this.saldos = saldos;
	}

	@Override
	public boolean equals(Object obj) {
	
		if(obj==null){
			return false;
		}
		if(!( obj instanceof TiendaCajaEfectivo)){
			return false;
		}
		
		TiendaCajaEfectivo tc= (TiendaCajaEfectivo)obj;
		
		if(tc.tienda==null){
			return false;
		}
		
		return  tc.getId()==this.id&& tc.getTienda().getId()==this.getTienda().getId();
		
	}

	@Override
	public int hashCode() {
	
		return  this.tienda.getId()+this.id;
	}
	
}
