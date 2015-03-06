package clases.business.metales.vo.cotizador;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;




@Entity
@Table(schema="public",name="Metal")
public class Metal  extends Producto implements Serializable{

	
	
	public Metal(){
		
	}
	
	public Metal(int id){
		super(id);
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
	private float porcentajePureza;
	
	
	
	 

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


	
	public float getPorcentajePureza() {
		return porcentajePureza;
	}

	public void setPorcentajePureza(float porcentajePureza) {
		this.porcentajePureza = porcentajePureza;
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
	
	

	
}
