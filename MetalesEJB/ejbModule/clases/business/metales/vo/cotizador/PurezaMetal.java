package clases.business.metales.vo.cotizador;

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
@Table(schema="public", name="metalpureza")
@SequenceGenerator(name="seqcatalogos", schema="public" ,sequenceName="SEQ_CATLOGOS", initialValue=-900000,allocationSize=1)
public class PurezaMetal implements Serializable{

	private static final long serialVersionUID = -4933276251420930274L;

		 
	@GeneratedValue(generator="seqcatalogos" )		
	@Id @Column(name="fiIdTipoPureza")
	private int id;
	
	@Column(name="fcNombre")
	private String nombre;
	@Column(name="fnProporcionPureza")
	private float proporcionPureza=-1-0F;
	@Column(name="fiIdEstatus")
	private int estatus;
	
	@Id
	@ManyToOne
	@JoinColumn(name="fiIdMetal")			
	private Metal metal;
	
	
	
	
	public PurezaMetal(){
		
	}
	
	public PurezaMetal(int id, Metal metal){
		this.id= id;
		this.metal= metal;
	}
	 
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Metal getMetal() {
		return metal;
	}

	public void setMetal(Metal metal) {
		this.metal = metal;
	}

	

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getProporcionPureza() {
		return proporcionPureza;
	}
	public void setProporcionPureza(float proporcionPureza) {
		this.proporcionPureza = proporcionPureza;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof PurezaMetal)){
			return false;
		}
		
		PurezaMetal po=(PurezaMetal)obj;
		
		return this.id==po.getId();
		
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	
	
	
	
}


