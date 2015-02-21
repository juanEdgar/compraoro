package clases.business.metales.vo.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import clases.login.UsuarioSesionTienda;
import clases.persistence.jpa.commun.embeddable.UsuarioModifico;
import clases.vo.catalogo.Estatus;
import clases.vo.cliente.Cliente;
import clases.vo.tienda.Tienda;

@Entity
@Table(schema="public", name="CompraMetal")
@SequenceGenerator(name="seq_compraMetal",schema="public", sequenceName="seq_compraMetal",allocationSize=10)
public class CompraMetal  implements Serializable {

	static final long serialVersionUID = -8275342757424131090L;
	
	public CompraMetal(){
		this.estatus= new Estatus(1);
	}
	
	
	public CompraMetal(String usuarioModifico){
		this.usuarioModifico= new UsuarioModifico(usuarioModifico);
	}
	
	
	@Id @GeneratedValue(generator="seq_compraMetal")
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
	
	@OneToMany(mappedBy="compraMetal")
	@OrderBy("Metal.id ASC, purezaMetal.nombre")
	@Basic(fetch=FetchType.EAGER)
	private List<ArticuloCompraMetal> articulos;
	
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


	public List<ArticuloCompraMetal> getArticulos() {
		return articulos;
	}


	public void setArticulos(List<ArticuloCompraMetal> articulos) {
		this.articulos = articulos;
	}


	public void setUsuarioModifico(UsuarioModifico usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	
	
	
	
	
}
