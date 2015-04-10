package clases.vo.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="Estado")
@SequenceGenerator(name="seq_estado", schema="public", sequenceName="seq_estado", allocationSize=1 )
public class Estado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1288391659169215787L;
	
	public Estado() {}
	
	public Estado(int id){
		this.setId(id);
	}

	@GeneratedValue(generator="seq_estado")
	@Column(name="fiIdEstado")
	@Id
	private int id;
	
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
}
