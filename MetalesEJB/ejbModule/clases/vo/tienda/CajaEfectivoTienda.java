package clases.vo.tienda;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;


@Entity
@Table(schema="public", name="CajaEfectivoTienda")
@SequenceGenerator(schema="public", sequenceName="seq_caja", name="seq_caja", allocationSize=10 )
public class CajaEfectivoTienda {

	@Id
	@GeneratedValue(generator="seq_caja")
	@Column(name="fiIdCajaSucursal")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fiidtienda")
	private Tienda tienda;
	
	@Column(name="fiIdEstatus")
	private int estatus;
	
	@OneToMany(mappedBy="cajaTienda")
	@OrderBy("denominacion asc")
	@Basic(fetch=FetchType.EAGER)
	private List<DotacionCajaEfectivoTienda> dotaciones;
	
	private UsuarioModifico usuarioModifico;

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
		if(!( obj instanceof CajaEfectivoTienda)){
			return false;
		}
		
		return ((CajaEfectivoTienda)obj).id== this.id;
		
	}

	@Override
	public int hashCode() {
	
		return  this.id;
	}
	
}
