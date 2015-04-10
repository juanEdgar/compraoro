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
@Table(schema="public", name="Colonia")
@SequenceGenerator(name="seq_colonia", schema="public", sequenceName="seq_colonia", allocationSize=1 )

public class Colonia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2437359714085224212L;
	public Colonia() {
		
	}
	
	public Colonia(int id){
		this.setId(id);
	}
	
	@GeneratedValue(generator="seq_colonia")
	@Column(name="fiIdColonia")
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fkMunicipio", referencedColumnName="fiIdMunicipio")
	private Municipio municipio;
	
	@Column(name="fcNombre")
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

	public 	Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
}

