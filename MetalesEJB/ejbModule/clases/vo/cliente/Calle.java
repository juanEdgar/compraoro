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
@Table(schema="public", name="Calle")
@SequenceGenerator(name="seq_calle", schema="public", sequenceName="seq_calle", allocationSize=50 )
public class Calle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922037233796269698L;

public Calle() {
		
	}
	
	public Calle(int id){
		this.setId(id);
	}
	
	@GeneratedValue(generator="seq_calle")
	@Column(name="fiIdCalle")
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name="fkColonia", referencedColumnName="fiIdColonia")
	private Colonia colonia;
	
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

	public 	Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}
}


