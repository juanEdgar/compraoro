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

@Entity
@Table(schema="public", name="CodigoPostal")
@SequenceGenerator(name="seq_codigopostal", schema="public", sequenceName="seq_codigopostal", allocationSize=1 )

public class CodigoPostal  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8933762408063459982L;

public CodigoPostal() {
		
	}
	
	public CodigoPostal(int id){
		this.setId(id);
	}
	
	@GeneratedValue(generator="seq_codigopostal")
	@Column(name="fiIdCodigoPostal")
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fkMunicipio", referencedColumnName="fiIdMunicipio")
	private Municipio municipio;
	
	@Column(name="fcCodigoPostal")
	private String codigoPostal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return codigoPostal;
	}

	public 	Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}


