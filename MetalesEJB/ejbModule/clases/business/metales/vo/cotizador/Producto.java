package clases.business.metales.vo.cotizador;

import javax.persistence.Embeddable;


@Embeddable
public  class Producto {
	
	protected String nombre;
	protected int id;
	
	
	public Producto(){
		
	}
	

	@Override
	public boolean equals(Object obj) {
		
	
		if(obj==null){
			return false;
		}
		if(!(obj instanceof Producto)){
			return false;
		}
		
		return this.id==((Producto)obj).getId();
		
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
	public Producto(int id){
		this.id=id;
	}

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
	
	
	
}
