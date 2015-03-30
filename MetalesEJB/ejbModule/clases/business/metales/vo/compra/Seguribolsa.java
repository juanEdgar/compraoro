package clases.business.metales.vo.compra;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import clases.persistence.jpa.commun.embeddable.UsuarioModifico;


@Entity
@Table(schema="public", name="SeguriBolsa")
@SequenceGenerator(schema="public", sequenceName="seq_seguribolsa", name="seq_seguribolsa", allocationSize=10)
public class Seguribolsa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4824359182983782242L;
	
	public Seguribolsa() {
		
	}
	
	public Seguribolsa(int id) {
		this.id=id;
	}
	
	@Id @GeneratedValue(generator="seq_seguribolsa")
	@Column(name="fiIdSeguribolsa")
	private int id;
	@Column(name="fcCodigoBolsa")
	private String codigo;
	@Column(name="fiIdEstatus")
	private int estatus;
	
	
	@ManyToOne
	@JoinColumn(name="fiIdCompra")
	private Compra compra;
	
	
	@OneToMany(mappedBy="seguribolsa")
	@Basic(fetch=FetchType.EAGER)
	private List<ArticuloCompra> articulosCompra;
	
	@Transient
	private String tipo;
	
	
	private UsuarioModifico usuarioModifico;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo.toUpperCase();
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

	public List<ArticuloCompra> getArticulosCompra() {
		return articulosCompra;
	}

	public void setArticulosCompra(List<ArticuloCompra> articulosCompra) {
		this.articulosCompra = articulosCompra;
	}
	
	
	
	
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public boolean equals(Object obj) {
	
		if(obj==null){
			return false;
		}
		
		if( !( obj instanceof Seguribolsa ) ){
			return false;
		}
		
		return ((Seguribolsa)obj).getId()==this.getId();
		
		
	
	}
	
	@Override
	public int hashCode() {		
		return this.getId();
	}
	
	

}
