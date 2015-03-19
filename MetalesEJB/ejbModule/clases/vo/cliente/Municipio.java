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
@Table(schema="public", name="Municipio")
@SequenceGenerator(name="seq_municipio", schema="public", sequenceName="seq_municipio", allocationSize=50 )

public class Municipio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6855165740799338352L;
	
	public Municipio() {
		
	}
	
	public Municipio(int id){
		this.setId(id);
	}
	
	@GeneratedValue(generator="seq_municipio")
	@Column(name="fiIdMunicipio")
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fkEstado", referencedColumnName="fiIdEstado")
	private Estado estado;
	
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
