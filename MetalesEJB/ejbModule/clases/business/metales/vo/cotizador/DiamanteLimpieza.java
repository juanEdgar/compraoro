package clases.business.metales.vo.cotizador;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="public", name="DiamanteLimpieza")
public class DiamanteLimpieza implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5757793981171400664L;




	public DiamanteLimpieza() {
	}
	
	public DiamanteLimpieza(int id) {
		this.id=id;
	}
	
	@Id
	@Column(name="fiIdDiamanteLimpieza")	
	private int id;
	
	@Column(name="fcLimpieza")
	private String limpieza;
	
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLimpieza() {
		return limpieza;
	}

	public void setLimpieza(String limpieza) {
		this.limpieza = limpieza;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof DiamanteLimpieza)){
			return false;
		}
		
		return ((DiamanteLimpieza)obj).getId()==this.getId();
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
	
}
