package clases.business.metales.vo.compra;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import clases.vo.tienda.Tienda;

@Entity
@Table(schema="public", name="CompraMetal")
public class CompraMetal  implements Serializable {

	static final long serialVersionUID = -8275342757424131090L;
	
	public CompraMetal(){
		this.estatus= new Estatus(1);
	}
	
	
	public CompraMetal(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	
	@Id
	@Column(name="fiIdCompraMetal")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fiIdTienda")
	private Tienda tienda;
	
	@ManyToOne
	@JoinColumn(name="fiIdPersona")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name="fiIdEstatus")
	private Estatus estatus;
	
	@OneToMany(mappedBy="compra")
	@OrderBy("id ASC")
	@Basic(fetch=FetchType.EAGER)
	private List<ArticuloCompra> articulos;
	
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


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Estatus getEstatus() {
		return estatus;
	}


	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}


	public List<ArticuloCompra> getArticulos() {
		return articulos;
	}


	public void setArticulos(List<ArticuloCompra> articulos) {
		this.articulos = articulos;
	}


	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	public UsuarioModifico getUsuarioModifico() {
		return usuarioModifico;
	}
	
	
	
	
	
}
