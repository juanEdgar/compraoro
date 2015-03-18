package clases.business.metales.vo.cotizador;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="DiamanteColor")
public class DiamanteColor  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2302135236717192053L;

	public DiamanteColor() {
	}
	
	public DiamanteColor(int id) {
		this.id= id;
	}
	
	@Id
	@Column(name="fiIdDiamanteColor")
	private int id;
	
	@Column(name="fcColor")
	private String color;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof DiamanteColor)){
			return false;
		}
		
		return ((DiamanteColor)obj).getId()==this.getId();
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
	
	

}
