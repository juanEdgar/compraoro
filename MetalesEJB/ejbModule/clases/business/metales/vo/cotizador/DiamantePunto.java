package clases.business.metales.vo.cotizador;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="DiamantePunto")
public class DiamantePunto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1514349329076885823L;

	public DiamantePunto() {
	}
	
	public DiamantePunto(int id) {
		this.id=id;
	}
	
	
	@Id
	@Column(name="fiIdDiamantePunto")
	private int id;
	
	@Column(name="fcRangoPuntos")
	private String rangoPuntos;
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRangoPuntos() {
		return rangoPuntos;
	}

	public void setRangoPuntos(String rangoPuntos) {
		this.rangoPuntos = rangoPuntos;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof DiamantePunto)){
			return false;
		}
		
		return ((DiamantePunto)obj).getId()==this.getId();
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	

}
