package clases.business.metales.vo.cotizador;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(schema="public",name="Metal")
public class Metal implements Serializable{

	
	
	public Metal(){
		
	}
	
	public Metal(int id){
		this.id=id;
	}
	
	private static final long serialVersionUID = -5106256075968215050L;
	
	
	@Id @Column(name="fiIdMetal")	
	private int id;
	@Column(name="fcNombre")
	private String nombre;
	@Column(name="fiIdEstatus")
	private int estatus;
	@Column(name="fnPurezaBase")
	private float purezaBase=0.0F;
	
	@OneToMany(mappedBy="metal")	
	@OrderBy("proporcionPureza desc")
	@Basic(fetch=FetchType.EAGER)
	private List< PurezaMetal> listaPurezas;
	
	@OneToOne(mappedBy="metal"  )
	private PrecioMetal precioGramo;
	
	@Transient
	private PurezaMetal purezaMetal;
	
	
	public float getCantidadMetalFino() throws Exception{
		
		if(this.purezaMetal==null || this.purezaMetal.getId()==0 || this.purezaMetal.getProporcionPureza()<0.0F){
			throw new Exception("Pureza nula, no se pueda calcular cantidad de metal fino");
		}
		
	
		if(this.purezaBase==0.0F){
			throw new Exception("No existe la pureza base del metal");
		}
		
		// se calcula la cantidad de metal fino del tipo de pureza correspondiente
		// proprcion de pureza entre pureza base del metal
		return this.getPurezaMetal().getProporcionPureza()/this.purezaBase;
		     
		
	}
	
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public float getPurezaBase() {
		return purezaBase;
	}

	public void setPurezaBase(float purezaBase) {
		this.purezaBase = purezaBase;
	}


	public PrecioMetal getPrecioGramo() {
		return precioGramo;
	}

	public void setPrecioGramo(PrecioMetal precioGramo) {
		this.precioGramo = precioGramo;
	}

	public List<PurezaMetal> getListaPurezas() {
		return listaPurezas;
	}

	public void setListaPurezas(List<PurezaMetal> listaPurezas) {
		this.listaPurezas = listaPurezas;
	}

	public PurezaMetal getPurezaMetal() {
		return purezaMetal;
	}

	public void setPurezaMetal(PurezaMetal purezaMetal) {
		this.purezaMetal = purezaMetal;
	}
	
	@Override
	public boolean equals(Object obj) {
		
	
		
		if(obj==null){
			return false;
		}
		if(!(obj instanceof Metal)){
			return false;
		}
		
		
		return this.id==((Metal)obj).getId();
		
	}
	
	@Override
	public int hashCode() {
		
		return this.id;
	}
	

	
}
