package clases.vo.cliente;

import java.io.Serializable;

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
@Table(schema="public", name="Direccion")
@SequenceGenerator(name="seq_direccion", schema="public", sequenceName="seq_direccion", allocationSize=1 )
public class Direccion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993445439354901595L;
	
	public Direccion() {
		
	}
	
	public Direccion(int id){
		this.setId(id);
	}
	
	@GeneratedValue(generator="seq_direccion")
	@Column(name="fiIdDireccion")
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fkColonia", referencedColumnName="fiIdColonia")
	private Colonia colonia;
	
	@ManyToOne
	@JoinColumn(name="fkCalle", referencedColumnName="fiIdCalle")
	private Calle calle;
	
	@ManyToOne
	@JoinColumn(name="fkCodigoPostal", referencedColumnName="fiIdCodigoPostal")
	private CodigoPostal codigo;
	
	@Column(name="fcNumeroExterior")
	private String numeroExterior;
	
	@Column(name="fcNumeroInterior")
	private String numeroInterior;

	private UsuarioModifico usuarioModificoCliente;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return calle.toString() + " " + getNumeroExterior();
	}
	
	public void setUsuarioModificoCliente(UsuarioModifico usuarioModificoCliente) {
		this.usuarioModificoCliente = usuarioModificoCliente;
	}

	public 	Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}
	
	public 	Calle getCalle() {
		return calle;
	}

	public void setCalle(Calle calle) {
		this.calle = calle;
	}
	
	public 	CodigoPostal getCodigo() {
		return codigo;
	}

	public void setCodigo(CodigoPostal codigo) {
		this.codigo = codigo;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public UsuarioModifico getUsuarioModificoCliente() {
		return usuarioModificoCliente;
	}
}


