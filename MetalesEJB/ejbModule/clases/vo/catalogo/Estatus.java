package clases.vo.catalogo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="catalogoEstatus")
public class Estatus implements Serializable {
	
	
	public Estatus(){
		
	}
	public Estatus(int idEstatus){
		this.id=idEstatus;
	}
	

	private static final long serialVersionUID = 3104925928869238323L;

	@Id
	@Column(name="fiIdEstatus",insertable=false,updatable=false)
	int id;	
		
	@Column(name="fcDescripcion",insertable=false,updatable=false)
	String descripcion;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

}
